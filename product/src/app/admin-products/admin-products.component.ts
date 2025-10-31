import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminProductService, ProductDTO } from '../service/admin-product.service';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {
  products: ProductDTO[] = [];
  filteredProducts: ProductDTO[] = [];
  isLoading = true;
  searchQuery: string = '';
  filterStatus: string = 'all';
  filterCategory: string = 'all';
  
  showProductModal = false;
  showStockModal = false;
  isEditMode = false;
  
  // Current product being edited/created
  currentProduct: ProductDTO = this.getEmptyProduct();
  stockProduct: ProductDTO | null = null;
  newStockValue: number = 0;
  
  // Categories for dropdown
  categories: string[] = [];

  constructor(private adminProductService: AdminProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.isLoading = true;
    this.adminProductService.getAllProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.extractCategories();
        this.applyFilters();
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading products:', error);
        alert('Failed to load products');
        this.isLoading = false;
      }
    });
  }

  extractCategories(): void {
    const categorySet = new Set(this.products.map(p => p.categoryName).filter(c => c));
    this.categories = Array.from(categorySet).sort();
  }

  applyFilters(): void {
    this.filteredProducts = this.products.filter(product => {
      const matchesSearch = !this.searchQuery || 
        product.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        (product.brand && product.brand.toLowerCase().includes(this.searchQuery.toLowerCase())) ||
        (product.sku && product.sku.toLowerCase().includes(this.searchQuery.toLowerCase()));
      
      const matchesStatus = this.filterStatus === 'all' ||
        (this.filterStatus === 'low-stock' && product.stockQuantity <= 5 && product.stockQuantity > 0) ||
        (this.filterStatus === 'out-of-stock' && product.stockQuantity === 0) ||
        (this.filterStatus === 'active' && product.stockQuantity > 5);
      
      const matchesCategory = this.filterCategory === 'all' || product.categoryName === this.filterCategory;
      
      return matchesSearch && matchesStatus && matchesCategory;
    });
  }

  onSearchChange(): void {
    this.applyFilters();
  }

  onFilterChange(): void {
    this.applyFilters();
  }

  // Modal actions
  openCreateModal(): void {
    this.isEditMode = false;
    this.currentProduct = this.getEmptyProduct();
    this.showProductModal = true;
  }

  openEditModal(product: ProductDTO): void {
    this.isEditMode = true;
    this.currentProduct = { ...product };
    this.showProductModal = true;
  }

  closeProductModal(): void {
    this.showProductModal = false;
    this.currentProduct = this.getEmptyProduct();
  }

  openStockModal(product: ProductDTO): void {
    this.stockProduct = product;
    this.newStockValue = product.stockQuantity;
    this.showStockModal = true;
  }

  closeStockModal(): void {
    this.showStockModal = false;
    this.stockProduct = null;
  }

  // CRUD operations
  saveProduct(): void {
    if (!this.validateProduct()) {
      return;
    }

    if (this.isEditMode && this.currentProduct.id) {
      this.updateProduct();
    } else {
      this.createProduct();
    }
  }

  createProduct(): void {
    this.adminProductService.createProduct(this.currentProduct).subscribe({
      next: (created) => {
        alert('Product created successfully!');
        this.loadProducts();
        this.closeProductModal();
      },
      error: (error) => {
        console.error('Error creating product:', error);
        alert('Failed to create product');
      }
    });
  }

  updateProduct(): void {
    if (!this.currentProduct.id) return;
    
    this.adminProductService.updateProduct(this.currentProduct.id, this.currentProduct).subscribe({
      next: (updated) => {
        alert('Product updated successfully!');
        this.loadProducts();
        this.closeProductModal();
      },
      error: (error) => {
        console.error('Error updating product:', error);
        alert('Failed to update product');
      }
    });
  }

  deleteProduct(product: ProductDTO): void {
    if (!product.id) return;
    
    if (confirm(`Are you sure you want to delete "${product.name}"? This action cannot be undone.`)) {
      this.adminProductService.deleteProduct(product.id).subscribe({
        next: () => {
          alert('Product deleted successfully!');
          this.loadProducts();
        },
        error: (error) => {
          console.error('Error deleting product:', error);
          alert('Failed to delete product');
        }
      });
    }
  }

  updateStock(): void {
    if (!this.stockProduct || !this.stockProduct.id) return;
    
    this.adminProductService.updateStock(this.stockProduct.id, this.newStockValue).subscribe({
      next: (updated) => {
        alert('Stock updated successfully!');
        this.loadProducts();
        this.closeStockModal();
      },
      error: (error) => {
        console.error('Error updating stock:', error);
        alert('Failed to update stock');
      }
    });
  }

  validateProduct(): boolean {
    if (!this.currentProduct.name || this.currentProduct.name.trim() === '') {
      alert('Product name is required');
      return false;
    }
    if (!this.currentProduct.categoryName) {
      alert('Category is required');
      return false;
    }
    if (!this.currentProduct.price || this.currentProduct.price <= 0) {
      alert('Valid price is required');
      return false;
    }
    if (this.currentProduct.stockQuantity === undefined || this.currentProduct.stockQuantity < 0) {
      alert('Valid stock quantity is required');
      return false;
    }
    return true;
  }

  getEmptyProduct(): ProductDTO {
    return {
      name: '',
      description: '',
      productType: 'footwear',
      categoryName: '',
      subCategory: '',
      unitOfMeasure: '',
      price: 0,
      imageFile: '',
      additionalImages: '',
      brand: '',
      color: '',
      gender: '',
      stockQuantity: 0,
      sku: '',
      discountPercentage: 0,
      isFeatured: false,
      isNewArrival: false,
      isBestSeller: false,
      rating: 0,
      reviewCount: 0,
      status: 'Active'
    };
  }

  formatCurrency(amount: number): string {
    return '₱' + amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
  }

  getStockClass(stock: number): string {
    if (stock === 0) return 'out-of-stock';
    if (stock <= 5) return 'low-stock';
    return 'in-stock';
  }

  getStockLabel(stock: number): string {
    if (stock === 0) return 'Out of Stock';
    if (stock <= 5) return 'Low Stock';
    return 'In Stock';
  }
}