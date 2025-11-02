import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AdminService, DashboardStats, Customer } from '../service/admin.service';
import { AdminReviewService } from '../service/admin-review.service';
import { Review } from '../model/review';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  stats: DashboardStats | null = null;
  customers: Customer[] = [];
  allReviews: Review[] = [];
  filteredReviews: Review[] = [];
  isLoading = true;
  activeTab: 'overview' | 'orders' | 'products' | 'reviews' | 'customers' = 'overview';
  reviewFilter: 'all' | 'pending' | 'approved' = 'all';
  pendingReviewsCount = 0;

  constructor(
    private adminService: AdminService,
    private adminReviewService: AdminReviewService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
    this.loadReviews();
  }

  loadDashboardData(): void {
    this.isLoading = true;
    
    this.adminService.getDashboardStats().subscribe({
      next: (stats) => {
        this.stats = stats;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading dashboard stats:', error);
        this.isLoading = false;
      }
    });

    this.adminService.getAllCustomers().subscribe({
      next: (customers) => {
        this.customers = customers;
      },
      error: (error) => {
        console.error('Error loading customers:', error);
      }
    });
  }

  loadReviews(): void {
    this.adminReviewService.getAllReviews().subscribe({
      next: (reviews: Review[]) => {
        this.allReviews = reviews;
        this.pendingReviewsCount = reviews.filter(r => !r.isApproved).length;
        this.filterReviews();
      },
      error: (error: any) => {
        console.error('Error loading reviews:', error);
      }
    });
  }

  setReviewFilter(filter: 'all' | 'pending' | 'approved'): void {
    this.reviewFilter = filter;
    this.filterReviews();
  }

  filterReviews(): void {
    switch (this.reviewFilter) {
      case 'pending':
        this.filteredReviews = this.allReviews.filter(r => !r.isApproved);
        break;
      case 'approved':
        this.filteredReviews = this.allReviews.filter(r => r.isApproved);
        break;
      default:
        this.filteredReviews = this.allReviews;
    }
  }

  approveReview(reviewId: number): void {
    if (confirm('Are you sure you want to approve this review?')) {
      this.adminReviewService.approveReview(reviewId).subscribe({
        next: () => {
          alert('Review approved successfully!');
          this.loadReviews();
        },
        error: (error: unknown) => {
          console.error('Error approving review:', error);
          alert('Failed to approve review');
        }
      });
    }
  }

  rejectReview(reviewId: number): void {
    if (confirm('Are you sure you want to reject this review?')) {
      this.adminReviewService.rejectReview(reviewId).subscribe({
        next: () => {
          alert('Review rejected successfully!');
          this.loadReviews();
        },
        error: (error: unknown) => {
          console.error('Error rejecting review:', error);
          alert('Failed to reject review');
        }
      });
    }
  }

  deleteReview(reviewId: number): void {
    if (confirm('Are you sure you want to permanently delete this review? This action cannot be undone.')) {
      this.adminReviewService.deleteReview(reviewId).subscribe({
        next: () => {
          alert('Review deleted successfully!');
          this.loadReviews();
        },
        error: (error: unknown) => {
          console.error('Error deleting review:', error);
          alert('Failed to delete review');
        }
      });
    }
  }

  switchTab(tab: 'overview' | 'orders' | 'products' | 'reviews' | 'customers'): void {
    this.activeTab = tab;
  }

  navigateToOrders(): void {
    this.router.navigate(['/admin/orders']);
  }

  navigateToProducts(): void {
    this.router.navigate(['/admin/products']);
  }

  navigateToCustomers(): void {
    this.router.navigate(['/admin/customers']);
  }

  formatCurrency(amount: number): string {
    return '₱' + amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
  }

  formatDate(dateString: string | Date | undefined): string {
    if (!dateString) return 'N/A';
    const date = typeof dateString === 'string' ? new Date(dateString) : dateString;
    return date.toLocaleDateString('en-US', { 
      year: 'numeric', 
      month: 'short', 
      day: 'numeric' 
    });
  }
}