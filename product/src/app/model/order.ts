export class Order {
    id: number = 0;
    orderNumber: string = '';
    userId: number = 0;
    customerName: string = '';
    customerEmail: string = '';
    customerPhone: string = '';
    shippingAddress: string = '';
    shippingCity: string = '';
    shippingProvince: string = '';
    shippingPostalCode: string = '';
    totalAmount: number = 0;
    totalItems: number = 0;
    paymentMethod: string = 'COD';
    status: string = 'Pending';
    notes: string = '';
    orderDate: Date = new Date();
    lastUpdated: Date = new Date();
    orderItems: OrderItem[] = [];
}

export class OrderItem {
    id: number = 0;
    productId: number = 0;
    productName: string = '';
    productImage: string = '';
    productPrice: number = 0;
    quantity: number = 0;
    subtotal: number = 0;
    productColor: string = '';
    productSize: string = '';
    productBrand: string = '';
}

export interface CheckoutRequest {
    userId: number;
    customerName: string;
    customerEmail: string;
    customerPhone: string;
    shippingAddress: string;
    shippingCity: string;
    shippingProvince: string;
    shippingPostalCode: string;
    totalAmount: number;
    totalItems: number;
    paymentMethod: string;
    notes: string;
    orderItems: OrderItem[];
}