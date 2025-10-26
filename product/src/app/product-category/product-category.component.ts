import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../service/product.service';
import { CartService } from '../service/cart.service';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-category',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})
export class ProductCategoryComponent implements OnInit {
  productsCategory: any[] = [];
  isLoading = true;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getData().subscribe({
      next: (data: any) => {
        this.productsCategory = data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading products:', error);
        this.isLoading = false;
      }
    });
  }

  addToCart(product: any, event: Event): void {
    event.stopPropagation();

    if (!this.authService.isLoggedIn()) {
      if (confirm('Please login to add items to cart. Would you like to login now?')) {
        this.router.navigate(['/login']);
      }
      return;
    }

    this.cartService.addToCart(product, 1);
    alert(`${product.name} added to cart!`);
  }

  isOutOfStock(product: any): boolean {
    return product.stockQuantity === 0;
  }

  calculateDiscountedPrice(product: any): number {
    return product.price * (1 - product.discountPercentage / 100);
  }

  formatPrice(price: number): string {
    return price.toFixed(2);
  }
}
