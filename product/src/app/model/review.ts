export interface Review {
  id?: number;
  productId: number;
  userId: number;
  orderId?: number;
  rating: number;
  title?: string;
  comment?: string;
  verifiedPurchase?: boolean;
  isApproved?: boolean;
  customerName: string;
  customerEmail: string;
  helpfulCount?: number;
  notHelpfulCount?: number;
  createdAt?: Date;
  updatedAt?: Date;
}

export interface ReviewRequest {
  productId: number;
  rating: number;
  title?: string;
  comment?: string;
}

export interface ReviewSummary {
  productId: number;
  averageRating: number;
  totalReviews: number;
  fiveStarCount: number;
  fourStarCount: number;
  threeStarCount: number;
  twoStarCount: number;
  oneStarCount: number;
}