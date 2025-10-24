export class User {
    id: number = 0;
    username: string = '';
    email: string = '';
    firstName: string = '';
    lastName: string = '';
    phoneNumber: string = '';
    address: string = '';
    city: string = '';
    province: string = '';
    postalCode: string = '';
    role: string = '';
    isActive: boolean = true;
    createdAt: Date = new Date();
    lastLogin: Date = new Date();
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    role: string;
    token?: string;
    message: string;
}

export interface SignupRequest {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
}