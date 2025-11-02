import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review } from '../model/review';

@Injectable({
  providedIn: 'root'
})
export class AdminReviewService {
  private apiUrl = 'http://localhost:8080/api/admin/reviews';

  constructor(private http: HttpClient) {}

  getAllReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/all`);
  }

  getPendingReviews(): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/pending`);
  }

  approveReview(reviewId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${reviewId}/approve`, {});
  }

  rejectReview(reviewId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${reviewId}/reject`, {});
  }

  deleteReview(reviewId: number): Observable<{message: string}> {
    return this.http.delete<{message: string}>(`${this.apiUrl}/${reviewId}`);
  }

  getProductReviews(productId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/product/${productId}`);
  }

  getUserReviews(userId: number): Observable<Review[]> {
    return this.http.get<Review[]>(`${this.apiUrl}/user/${userId}`);
  }
}