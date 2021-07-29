import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatToolbarModule} from '@angular/material/toolbar';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EditComponentComponent } from './edit-component/edit-component.component';
import { LoginComponentComponent } from './login-component/login-component.component';
import { SignupComponent } from './signup/signup.component';
import { ForgotpswdComponent } from './forgotpswd/forgotpswd.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';  
import {DemoMaterialModule} from './material-module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LogoutComponent } from './logout/logout.component';
import {HttpClientModule } from '@angular/common/http';
import {MatDialogActions, MatDialogContent, MatDialogModule, MatDialogTitle } from '@angular/material/dialog';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { ReusabledialogComponent } from './reusabledialog/reusabledialog.component'



@NgModule({
  declarations: [
    AppComponent,
    EditComponentComponent,
    LoginComponentComponent,
    SignupComponent,
    ForgotpswdComponent,
    LogoutComponent,
    ReusabledialogComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    DemoMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatDialogModule,
    MatProgressSpinnerModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents:[MatDialogTitle,MatDialogActions,MatDialogContent,MatDialogModule,ReusabledialogComponent]
})
export class AppModule { }
