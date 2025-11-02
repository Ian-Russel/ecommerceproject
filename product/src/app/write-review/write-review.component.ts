import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReviewRequest } from '../model/review';
import { ReviewService } from '../service/review.service';

@Component({
  selector: 'app-write-review',
  templateUrl: './write-review.component.html',
  styleUrls: ['./write-review.component.css']
})
export class WriteReviewComponent implements OnInit {
  @Input() productId!: number;
  @Input() userId?: number;
  @Output() reviewSubmitted = new EventEmitter<void>();

  reviewForm!: FormGroup;
  hoveredRating = 0;
  selectedRating = 0;
  loading = false;
  error = '';
  success = '';
  hasAlreadyReviewed = false;

  constructor(
    private fb: FormBuilder,
    private reviewService: ReviewService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.checkIfUserReviewed();
  }

  initForm(): void {
    this.reviewForm = this.fb.group({
      title: ['', [Validators.maxLength(200)]],
      comment: ['', [Validators.required, Validators.maxLength(2000)]],
      rating: [0, [Validators.required, Validators.min(1), Validators.max(5)]]
    });
  }

  checkIfUserReviewed(): void {
    if (this.userId && this.productId) {
      this.reviewService.hasUserReviewedProduct(this.productId, this.userId)
        .subscribe({
          next: (response) => {
            this.hasAlreadyReviewed = response.hasReviewed;
          },
          error: (err) => {
            console.error('Error checking review status:', err);
          }
        });
    }
  }

  selectRating(rating: number): void {
    this.selectedRating = rating;
    this.reviewForm.patchValue({ rating });
  }

  hoverRating(rating: number): void {
    this.hoveredRating = rating;
  }

  resetHover(): void {
    this.hoveredRating = 0;
  }

  getDisplayRating(): number {
    return this.hoveredRating || this.selectedRating;
  }

  onSubmit(): void {
    if (this.reviewForm.invalid) {
      Object.keys(this.reviewForm.controls).forEach(key => {
        this.reviewForm.get(key)?.markAsTouched();
      });
      return;
    }

    if (!this.userId) {
      this.error = 'You must be logged in to write a review';
      return;
    }

    this.loading = true;
    this.error = '';
    this.success = '';

    const reviewRequest: ReviewRequest = {
      productId: this.productId,
      rating: this.reviewForm.value.rating,
      title: this.reviewForm.value.title,
      comment: this.reviewForm.value.comment
    };

    this.reviewService.createReview(reviewRequest, this.userId).subscribe({
      next: () => {
        this.success = 'Review submitted successfully!';
        this.loading = false;
        this.reviewForm.reset();
        this.selectedRating = 0;
        this.hasAlreadyReviewed = true;
        this.reviewSubmitted.emit();
        
        // Clear success message after 3 seconds
        setTimeout(() => {
          this.success = '';
        }, 3000);
      },
      error: (err) => {
        this.loading = false;
        this.error = err.error || 'Failed to submit review. Please try again.';
        console.error('Error submitting review:', err);
      }
    });
  }

  get titleControl() {
    return this.reviewForm.get('title');
  }

  get commentControl() {
    return this.reviewForm.get('comment');
  }

  get ratingControl() {
    return this.reviewForm.get('rating');
  }
}