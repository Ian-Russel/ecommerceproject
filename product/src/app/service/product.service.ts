import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../model/product';
import { ProductCategory } from '../model/product-category';

export interface ProductSearchParams {
  query?: string;
  category?: string;
  brand?: string;
  color?: string;
  gender?: string;
  minPrice?: number;
  maxPrice?: number;
  sortBy?: string;
}

export interface FilterOptions {
  categories: string[];
  brands: string[];
  colors: string[];
  genders: string[];
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/product';

  constructor(private http: HttpClient) {}

  getData(): Observable<ProductCategory[]> {
    return this.http.get<ProductCategory[]>(this.apiUrl);
  }

  searchProducts(searchParams: ProductSearchParams): Observable<Product[]> {
    let params = new HttpParams();
    
    if (searchParams.query) {
      params = params.set('query', searchParams.query);
    }
    if (searchParams.category) {
      params = params.set('category', searchParams.category);
    }
    if (searchParams.brand) {
      params = params.set('brand', searchParams.brand);
    }
    if (searchParams.color) {
      params = params.set('color', searchParams.color);
    }
    if (searchParams.gender) {
      params = params.set('gender', searchParams.gender);
    }
    if (searchParams.minPrice !== undefined && searchParams.minPrice !== null) {
      params = params.set('minPrice', searchParams.minPrice.toString());
    }
    if (searchParams.maxPrice !== undefined && searchParams.maxPrice !== null) {
      params = params.set('maxPrice', searchParams.maxPrice.toString());
    }
    if (searchParams.sortBy) {
      params = params.set('sortBy', searchParams.sortBy);
    }

    return this.http.get<Product[]>(`${this.apiUrl}/search`, { params });
  }

  getFilterOptions(): Observable<FilterOptions> {
    return this.http.get<FilterOptions>(`${this.apiUrl}/filters`);
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }
}