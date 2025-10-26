import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../service/cart.service';
import { OrderService } from '../service/order.service';
import { AuthService } from '../service/auth.service';
import { Cart, CartItem } from '../model/cart';
import { CheckoutRequest, OrderItem } from '../model/order';
import { LoginResponse } from '../model/user';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  cart: Cart = new Cart();
  currentUser: LoginResponse | null = null;
  isProcessing: boolean = false;
  errorMessage: string = '';

  checkoutForm = {
    customerName: '',
    customerEmail: '',
    customerPhone: '',
    shippingAddress: '',
    shippingCity: '',
    shippingProvince: '',
    shippingPostalCode: '',
    paymentMethod: 'COD',
    notes: ''
  };

  provinces = [
    'Metro Manila', 'Cavite', 'Laguna', 'Batangas', 'Rizal', 'Bulacan',
    'Pampanga', 'Tarlac', 'Nueva Ecija', 'Pangasinan', 'Cebu', 'Davao'
  ];

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Check if user is logged in
    this.currentUser = this.authService.getCurrentUser();
    if (!this.currentUser) {
      alert('Please login to proceed with checkout');
      this.router.navigate(['/login']);
      return;
    }

    // Get cart
    this.cart = this.cartService.getCart();
    if (this.cart.items.length === 0) {
      alert('Your cart is empty');
      this.router.navigate(['/cart']);
      return;
    }

    // Pre-fill form with user data
    this.checkoutForm.customerName = `${this.currentUser.firstName} ${this.currentUser.lastName}`;
    this.checkoutForm.customerEmail = this.currentUser.email;
  }

  validateForm(): boolean {
    if (!this.checkoutForm.customerName.trim()) {
      this.errorMessage = 'Please enter your name';
      return false;
    }
    if (!this.checkoutForm.customerEmail.trim()) {
      this.errorMessage = 'Please enter your email';
      return false;
    }
    if (!this.checkoutForm.customerPhone.trim()) {
      this.errorMessage = 'Please enter your phone number';
      return false;
    }
    if (!this.checkoutForm.shippingAddress.trim()) {
      this.errorMessage = 'Please enter your shipping address';
      return false;
    }
    if (!this.checkoutForm.shippingCity.trim()) {
      this.errorMessage = 'Please enter your city';
      return false;
    }
    if (!this.checkoutForm.shippingProvince) {
      this.errorMessage = 'Please select your province';
      return false;
    }

    this.errorMessage = '';
    return true;
  }

  placeOrder(): void {
    if (!this.validateForm()) {
      return;
    }

    if (!confirm('Confirm order placement?')) {
      return;
    }

    this.isProcessing = true;
    this.errorMessage = '';

    const orderItems: OrderItem[] = this.cart.items.map(item => ({
      id: 0,
      productId: item.productId,
      productName: item.productName,
      productImage: item.productImage,
      productPrice: item.productPrice,
      quantity: item.quantity,
      subtotal: item.subtotal,
      productColor: item.color || '',
      productSize: item.size || '',
      productBrand: item.brand || ''
    }));

    // Create checkout request
    const checkoutRequest: CheckoutRequest = {
      userId: this.currentUser!.id,
      customerName: this.checkoutForm.customerName,
      customerEmail: this.checkoutForm.customerEmail,
      customerPhone: this.checkoutForm.customerPhone,
      shippingAddress: this.checkoutForm.shippingAddress,
      shippingCity: this.checkoutForm.shippingCity,
      shippingProvince: this.checkoutForm.shippingProvince,
      shippingPostalCode: this.checkoutForm.shippingPostalCode,
      totalAmount: this.cart.totalPrice,
      totalItems: this.cart.totalItems,
      paymentMethod: this.checkoutForm.paymentMethod,
      notes: this.checkoutForm.notes,
      orderItems: orderItems
    };

    // Submit order
    this.orderService.checkout(checkoutRequest).subscribe({
      next: (order) => {
        console.log('Order placed successfully:', order);
        this.cartService.clearCart();
        this.router.navigate(['/order-success'], { 
          queryParams: { orderNumber: order.orderNumber } 
        });
      },
      error: (error) => {
        console.error('Order failed:', error);
        this.errorMessage = error.error?.message || 'Failed to place order. Please try again.';
        this.isProcessing = false;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/cart']);
  }
}