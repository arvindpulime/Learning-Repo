import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditComponentComponent } from './edit-component/edit-component.component';
import { ForgotpswdComponent } from './forgotpswd/forgotpswd.component';
import { LoginComponentComponent } from './login-component/login-component.component';
import { LogoutComponent } from './logout/logout.component';
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
 {path: 'forgotpassword',component:ForgotpswdComponent},
 {path: 'editprofile',component:EditComponentComponent},
 {path: 'login',component:LoginComponentComponent},
 {path: 'signup',component:SignupComponent},
 {path: 'logout',component:LogoutComponent}
 
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
