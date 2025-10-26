import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../service/order.service';
import { AuthService } from '../service/auth.service';
import { Order } from '../model/order';

@Component({
  selector: 'app-product-order',
  templateUrl: './product-order.component.html',
  styleUrls: ['./product-order.component.css']
})
export class ProductOrderComponent implements OnInit {
  orders: Order[] = [];
  filteredOrders: Order[] = [];
  isLoading: boolean = true;
  selectedStatus: string = 'All';
  
  statusOptions = ['All', 'Pending', 'Processing', 'Shipped', 'Delivered', 'Cancelled'];

  constructor(
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const currentUser = this.authService.getCurrentUser();
    
    if (!currentUser) {
      alert('Please login to view your orders');
      this.router.navigate(['/login']);
      return;
    }

    this.loadOrders(currentUser.id);
  }

  loadOrders(userId: number): void {
    this.isLoading = true;
    
    this.orderService.getUserOrders(userId).subscribe({
      next: (orders) => {
        this.orders = orders;
        this.filteredOrders = orders;
        this.isLoading = false;
        console.log('Orders loaded:', orders);
      },
      error: (error) => {
        console.error('Error loading orders:', error);
        this.isLoading = false;
      }
    });
  }

  filterByStatus(status: string): void {
    this.selectedStatus = status;
    
    if (status === 'All') {
      this.filteredOrders = this.orders;
    } else {
      this.filteredOrders = this.orders.filter(order => order.status === status);
    }
  }

  viewOrderDetails(order: Order): void {
    this.router.navigate(['/order-details', order.id]);
  }

  getStatusClass(status: string): string {
    switch(status) {
      case 'Pending': return 'status-pending';
      case 'Processing': return 'status-processing';
      case 'Shipped': return 'status-shipped';
      case 'Delivered': return 'status-delivered';
      case 'Cancelled': return 'status-cancelled';
      default: return '';
    }
  }

  getStatusIcon(status: string): string {
    switch(status) {
      case 'Pending': return '⏳';
      case 'Processing': return '⚙️';
      case 'Shipped': return '🚚';
      case 'Delivered': return '✅';
      case 'Cancelled': return '❌';
      default: return '📦';
    }
  }

  continueShopping(): void {
    this.router.navigate(['/products']);
  }
}