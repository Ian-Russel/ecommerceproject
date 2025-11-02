// File: src/app/components/product-reviews/product-reviews.component.ts
import { Component, Input, OnInit } from '@angular/core';
import { Review, ReviewSummary } from '../model/review';
import { ReviewService } from '../service/review.service';

@Component({
  selector: 'app-product-reviews',
  templateUrl: './product-reviews.component.html',
  styleUrls: ['./product-reviews.component.css']
})
export class ProductReviewsComponent implements OnInit {
  @Input() productId!: number;
  @Input() currentUserId?: number;

  reviews: Review[] = [];
  reviewSummary?: ReviewSummary;
  loading = false;
  error = '';

  Math = Math;

  constructor(private reviewService: ReviewService) {}

  ngOnInit(): void {
    this.loadReviews();
    this.loadReviewSummary();
  }

  loadReviews(): void {
    this.loading = true;
    this.reviewService.getProductReviews(this.productId).subscribe({
      next: (reviews) => {
        this.reviews = reviews;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load reviews';
        this.loading = false;
        console.error('Error loading reviews:', err);
      }
    });
  }

  loadReviewSummary(): void {
    this.reviewService.getProductReviewSummary(this.productId).subscribe({
      next: (summary) => {
        this.reviewSummary = summary;
      },
      error: (err) => {
        console.error('Error loading review summary:', err);
      }
    });
  }

  markHelpful(reviewId: number, helpful: boolean): void {
    this.reviewService.markReviewHelpful(reviewId, helpful).subscribe({
      next: () => {
        this.loadReviews();
      },
      error: (err) => {
        console.error('Error marking review:', err);
      }
    });
  }

  getStarArray(rating: number): number[] {
    return Array(5).fill(0).map((_, i) => i < rating ? 1 : 0);
  }

  getRatingPercentage(count: number): number {
    if (!this.reviewSummary || this.reviewSummary.totalReviews === 0) {
      return 0;
    }
    return (count / this.reviewSummary.totalReviews) * 100;
  }

  getTimeAgo(date: Date): string {
    const now = new Date();
    const reviewDate = new Date(date);
    const diffMs = now.getTime() - reviewDate.getTime();
    const diffDays = Math.floor(diffMs / (1000 * 60 * 60 * 24));

    if (diffDays === 0) return 'Today';
    if (diffDays === 1) return 'Yesterday';
    if (diffDays < 7) return `${diffDays} days ago`;
    if (diffDays < 30) return `${Math.floor(diffDays / 7)} weeks ago`;
    if (diffDays < 365) return `${Math.floor(diffDays / 30)} months ago`;
    return `${Math.floor(diffDays / 365)} years ago`;
  }
}