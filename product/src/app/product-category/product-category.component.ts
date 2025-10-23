import { Component, OnInit } from '@angular/core';
import { Product } from '../model/product';
import { ProductCategory } from '../model/product-category';
import { ProductService } from '../service/product.service';

@Component({
  selector: 'app-product-category',
  templateUrl: './product-category.component.html',
  styleUrls: ['./product-category.component.css']
})
export class ProductCategoryComponent implements OnInit {
  public productsCategory: ProductCategory[] = [];
 
  constructor(private productService: ProductService) {}
  
  ngOnInit(): void {
    console.log("ngOnInit called");
    this.productService.getData().subscribe(data => {
      this.productsCategory = data;
    });
  }

  /**
   * Calculate the discounted price for a product
   */
  calculateDiscountedPrice(product: Product): number {
    if (product.discountPercentage > 0) {
      const discount = product.price * (product.discountPercentage / 100);
      return product.price - discount;
    }
    return product.price;
  }

  /**
   * Add product to cart
   */
  addToCart(product: Product): void {
    if (product.stockQuantity > 0) {
      console.log('Adding to cart:', product);
      // TODO: Implement cart service integration
      // Example: this.cartService.addToCart(product);
      
      // Optional: Show success message
      alert(`${product.name} added to cart!`);
    } else {
      alert('This product is out of stock');
    }
  }

  formatPrice(price: number): string {
  return price.toFixed(2);
``}

  /**
   * Open quick view modal for product details
   */
  quickView(product: Product): void {
    console.log('Quick view for:', product);
    // TODO: Implement modal/dialog service to show product details
    // Example: this.dialogService.open(ProductQuickViewComponent, { data: product });
    
    // Temporary: Show alert with product info
    alert(`Quick View: ${product.name}\n\n${product.description}\n\nPrice: ₱${this.calculateDiscountedPrice(product)}`);
  }
}