import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ProductDTO {
  id?: number;
  name: string;
  description: string;
  productType: string;
  categoryName: string;
  subCategory?: string;
  unitOfMeasure?: string;
  price: number;
  imageFile: string;
  additionalImages?: string;
  brand?: string;
  color?: string;
  gender?: string;
  stockQuantity: number;
  sku?: string;
  discountPercentage?: number;
  isFeatured?: boolean;
  isNewArrival?: boolean;
  isBestSeller?: boolean;
  rating?: number;
  reviewCount?: number;
  status?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminProductService {
  private apiUrl = 'http://localhost:8080/api/admin/products';

  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<ProductDTO[]> {
    return this.http.get<ProductDTO[]>(this.apiUrl);
  }

  getProductById(id: number): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(`${this.apiUrl}/${id}`);
  }

  createProduct(product: ProductDTO): Observable<ProductDTO> {
    return this.http.post<ProductDTO>(this.apiUrl, product);
  }

  updateProduct(id: number, product: ProductDTO): Observable<ProductDTO> {
    return this.http.put<ProductDTO>(`${this.apiUrl}/${id}`, product);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  updateStock(id: number, stock: number): Observable<ProductDTO> {
    return this.http.patch<ProductDTO>(`${this.apiUrl}/${id}/stock?stock=${stock}`, {});
  }

  getLowStockProducts(): Observable<ProductDTO[]> {
    return this.http.get<ProductDTO[]>(`${this.apiUrl}/low-stock`);
  }

  getOutOfStockProducts(): Observable<ProductDTO[]> {
    return this.http.get<ProductDTO[]>(`${this.apiUrl}/out-of-stock`);
  }
}