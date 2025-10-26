export class CartItem {
    id: number = 0;
    productId: number = 0;
    productName: string = '';
    productPrice: number = 0;
    productImage: string = '';
    quantity: number = 1;
    categoryName: string = '';
    
    color?: string;
    size?: string;
    brand?: string;
    
    get subtotal(): number {
        return this.productPrice * this.quantity;
    }
}

export class Cart {
    items: CartItem[] = [];
    
    get totalItems(): number {
        return this.items.reduce((sum, item) => sum + item.quantity, 0);
    }
    
    get totalPrice(): number {
        return this.items.reduce((sum, item) => sum + item.subtotal, 0);
    }
}