import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review, ReviewRequest, ReviewSummary } from '../model/review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private apiUrl = 'http://localhost:8080/api/reviews';

  constructor(private http: HttpClient) {}

  createReview(reviewRequest: ReviewRequest, userId: number): Observable<Review> {
    const headers = new HttpHeaders().set('userId', userId.toString());
    return this.http.post<Review>(this.apiUrl, reviewRequest, { headers });
  }

  getProductReviews(productId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/product/${productId}`);
  }

  getProductReviewSummary(productId: number): Observable<ReviewSummary> {
    return this.http.get<ReviewSummary>(`${this.apiUrl}/product/${productId}/summary`);
  }

  getUserReviews(userId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/user/${userId}`);
  }

  hasUserReviewedProduct(productId: number, userId: number): Observable<{hasReviewed: boolean}> {
    return this.http.get<{hasReviewed: boolean}>(
      `${this.apiUrl}/product/${productId}/user/${userId}/check`
    );
  }

  updateReview(reviewId: number, review: Review, userId: number): Observable<Review> {
    const headers = new HttpHeaders().set('userId', userId.toString());
    return this.http.put<Review>(`${this.apiUrl}/${reviewId}`, review, { headers });
  }

  deleteReview(reviewId: number, userId: number): Observable<any> {
    const headers = new HttpHeaders().set('userId', userId.toString());
    return this.http.delete(`${this.apiUrl}/${reviewId}`, { headers });
  }

  markReviewHelpful(reviewId: number, helpful: boolean): Observable<any> {
    return this.http.post(`${this.apiUrl}/${reviewId}/helpful?helpful=${helpful}`, {});
  }

  getPendingReviews(userRole: string): Observable<Review[]> {
    const headers = new HttpHeaders().set('userRole', userRole);
    return this.http.get<Review[]>(`${this.apiUrl}/pending`, { headers });
  }

  approveReview(reviewId: number, userRole: string): Observable<any> {
    const headers = new HttpHeaders().set('userRole', userRole);
    return this.http.post(`${this.apiUrl}/${reviewId}/approve`, {}, { headers });
  }
}