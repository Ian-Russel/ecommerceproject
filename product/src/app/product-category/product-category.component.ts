import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService, ProductSearchParams, FilterOptions } from '../service/product.service';
import { CartService } from '../service/cart.service';
import { AuthService } from '../service/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Product } from '../model/product';

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})
export class ProductCategoryComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  isLoading = true;
  
  filterOptions: FilterOptions = {
    categories: [],
    brands: [],
    colors: [],
    genders: []
  };
  
  searchQuery: string = '';
  selectedCategory: string = '';
  selectedBrand: string = '';
  selectedColor: string = '';
  selectedGender: string = '';
  minPrice: number | null = null;
  maxPrice: number | null = null;
  sortBy: string = 'name_asc';
  
  showFilters: boolean = true;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadFilterOptions();
    
    this.route.queryParams.subscribe(params => {
      if (params['search']) {
        this.searchQuery = params['search'];
      }
      this.loadProducts();
    });
  }

  loadFilterOptions(): void {
    this.productService.getFilterOptions().subscribe({
      next: (options: FilterOptions) => {
        this.filterOptions = options;
      },
      error: (error: any) => {
        console.error('Error loading filter options:', error);
      }
    });
  }

  loadProducts(): void {
    this.isLoading = true;
    const searchParams: ProductSearchParams = {
      query: this.searchQuery || undefined,
      category: this.selectedCategory || undefined,
      brand: this.selectedBrand || undefined,
      color: this.selectedColor || undefined,
      gender: this.selectedGender || undefined,
      minPrice: this.minPrice || undefined,
      maxPrice: this.maxPrice || undefined,
      sortBy: this.sortBy
    };

    this.productService.searchProducts(searchParams).subscribe({
      next: (data: Product[]) => {
        this.products = data;
        this.filteredProducts = data;
        this.isLoading = false;
      },
      error: (error: any) => {
        console.error('Error loading products:', error);
        this.isLoading = false;
      }
    });
  }

  onSearch(): void {
    this.loadProducts();
  }

  onFilterChange(): void {
    this.loadProducts();
  }

  onSortChange(): void {
    this.loadProducts();
  }

  clearFilters(): void {
    this.searchQuery = '';
    this.selectedCategory = '';
    this.selectedBrand = '';
    this.selectedColor = '';
    this.selectedGender = '';
    this.minPrice = null;
    this.maxPrice = null;
    this.sortBy = 'name_asc';
    this.loadProducts();
  }

  toggleFilters(): void {
    this.showFilters = !this.showFilters;
  }

  addToCart(product: Product, event: Event): void {
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

  isOutOfStock(product: Product): boolean {
    return product.stockQuantity === 0;
  }

  calculateDiscountedPrice(product: Product): number {
    return product.price * (1 - product.discountPercentage / 100);
  }

  formatPrice(price: number): string {
    return price.toFixed(2);
  }

  get resultsCount(): number {
    return this.filteredProducts.length;
  }

  get hasActiveFilters(): boolean {
    return !!(this.searchQuery || this.selectedCategory || this.selectedBrand || 
              this.selectedColor || this.selectedGender || this.minPrice || this.maxPrice);
  }
}