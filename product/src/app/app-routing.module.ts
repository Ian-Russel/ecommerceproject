import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainBodyComponent } from './main-body/main-body.component';
import { ProductCategoryComponent } from './product-category/product-category.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { ProductOrderComponent } from './product-order/product-order.component';
import { CustomerServiceComponent } from './customer-service/customer-service.component';
import { CompanyHomeComponent } from './company-home/company-home.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  { path: '', component: MainBodyComponent },
  { path: 'home', component: MainBodyComponent },
  { path: 'products', component: ProductCategoryComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  
  // Protected routes - require login
  { 
    path: 'cart', 
    component: ShoppingCartComponent,
    canActivate: [AuthGuard]  // Must be logged in
  },
  { 
    path: 'orders', 
    component: ProductOrderComponent,
    canActivate: [AuthGuard]  // Must be logged in
  },
  
  { path: 'customer', component: CustomerServiceComponent },
  { path: 'company', component: CompanyHomeComponent },
  { path: 'contact', component: ContactUsComponent },
  
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }