export class Product{
    id: number = 0;
    name: string = '';
    description: string = '';
    productType: string = 'footwear';  // 'footwear', 'bag', 'clothing'
    categoryName: string = '';
    subCategory: string = '';
    unitOfMeasure: string = '';
    price: number = 0;
    imageFile: string = '';
    additionalImages: string[] = [];
    
    brand: string = '';
    color: string = '';
    gender: string = '';
    
    stockQuantity: number = 0;
    sku: string = '';
    
    discountPercentage: number = 0;
    isFeatured: boolean = false;
    isNewArrival: boolean = false;
    isBestSeller: boolean = false;
    
    rating: number = 0;
    reviewCount: number = 0;
    
    attributes: any = {};  // Type-specific attributes
    specifications: any = {};
    
    status: string = 'Active';
    
    lastUpdated: Date = new Date();
    created: Date = new Date();
}