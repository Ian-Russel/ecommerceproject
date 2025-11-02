import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Cart, CartItem } from '../model/cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartSubject = new BehaviorSubject<Cart>(new Cart());
  public cart$ = this.cartSubject.asObservable();

  constructor() {
    this.loadCart();
  }

  private loadCart(): void {
    const savedCart = localStorage.getItem('shopping_cart');
    if (savedCart) {
      try {
        const parsed = JSON.parse(savedCart);
        const cart = new Cart();
        cart.items = parsed.items.map((i: any) => Object.assign(new CartItem(), i));
        this.cartSubject.next(cart);
        console.log('Cart loaded from storage:', cart);
      } catch (error) {
        console.error('Error loading cart:', error);
        localStorage.removeItem('shopping_cart');
      }
    }
  }

  private saveCart(): void {
    const cart = this.cartSubject.value;
    localStorage.setItem('shopping_cart', JSON.stringify(cart));
    console.log('Cart saved to storage:', cart);
  }

  addToCart(product: any, quantity: number = 1): void {
    const cart = this.cartSubject.value;
    const existingItemIndex = cart.items.findIndex(
      item => item.productId === product.id
    );

    if (existingItemIndex > -1) {
      cart.items[existingItemIndex].quantity += quantity;
    } else {
      const cartItem = new CartItem();
      cartItem.id = Date.now();
      cartItem.productId = product.id;
      cartItem.productName = product.name;
      cartItem.productPrice = product.price;
      cartItem.productImage = product.imageFile;
      cartItem.quantity = quantity;
      cartItem.categoryName = product.categoryName;
      cartItem.color = product.color;
      cartItem.size = product.attributes?.size;
      cartItem.brand = product.brand;

      cart.items.push(cartItem);
    }

    this.cartSubject.next(cart);
    this.saveCart();
    console.log('Added to cart:', product.name);
  }

  removeFromCart(cartItemId: number): void {
    const cart = this.cartSubject.value;
    cart.items = cart.items.filter(item => item.id !== cartItemId);
    this.cartSubject.next(cart);
    this.saveCart();
  }

  updateQuantity(cartItemId: number, quantity: number): void {
    if (quantity <= 0) {
      this.removeFromCart(cartItemId);
      return;
    }

    const cart = this.cartSubject.value;
    const item = cart.items.find(i => i.id === cartItemId);
    if (item) {
      item.quantity = quantity;
      this.cartSubject.next(cart);
      this.saveCart();
    }
  }

  clearCart(): void {
    this.cartSubject.next(new Cart());
    localStorage.removeItem('shopping_cart');
  }

  getCart(): Cart {
    return this.cartSubject.value;
  }

  getCartItemCount(): number {
    return this.cartSubject.value.totalItems;
  }

  getCartTotal(): number {
    return this.cartSubject.value.totalPrice;
  }
}
