import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MustMatch } from '../MustMatch';
import { ForgotpswdService } from '../Services/forgotpswd.service';
import { MatStepper } from '@angular/material/stepper';
import { UpdatepswdService } from '../Services/updatepswd.service';
import { MatDialog, MatDialogConfig, MatDialogRef } from '@angular/material/dialog';
import { ReusabledialogComponent } from '../reusabledialog/reusabledialog.component';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-forgotpswd',
  templateUrl: './forgotpswd.component.html',
  styleUrls: ['./forgotpswd.component.css']
})
export class ForgotpswdComponent implements OnInit {

  firstFormGroup: FormGroup
  secondFormGroup: FormGroup
  showspinner = false
  stepper: any
  updateflag: any
  pswddata = {

    employeeId: "",
    password: ""

  }
  pswdresetstatus = ""
  key: any
  data: any
  employee: any
  employeeId = ""
  retryscount = 2;
  errormessage = ""
  redirectflag = false
  constructor(private _formBuilder: FormBuilder, private router: Router, private dailog: MatDialog,
    private forgotpswdservice: ForgotpswdService, private updatepswdservice: UpdatepswdService,private toastr: ToastrService) {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(5)]],
      thirdCtrl: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(8)]],
      fourthCtrl: ['', Validators.required],
    }, {
      validator: MustMatch('thirdCtrl', 'fourthCtrl')
    });
  }
  ngOnInit() {
    this.firstFormGroup.reset()
    this.secondFormGroup.reset()

  }
  get form1() {
    return this.firstFormGroup.controls;
  }
  get form2() {
    return this.secondFormGroup.controls;
  }
  gotoLogin() {
    this.router.navigate(['login'])
  }
  sendmail(stepper: MatStepper) {
    console.log(this.form1.firstCtrl.value);
    this.stepper = stepper;
    this.CallSpinnerDailog();
    this.mailservice();

  }
  mailservice() {
    this.employeeId = this.form1.firstCtrl.value;
    this.forgotpswdservice.sendmail(this.form1.firstCtrl.value)
      .subscribe(
        res => {
          console.log(res);
          this.timeoutfunction(res)
        },
        error => {
          this.errormessage = error;
          console.log("error check");
          console.log(this.errormessage)

          setTimeout(() => {
            this.dailog.closeAll()
            this.gotoLogin()
          }, 50);

        }
      );

  }

  timeoutfunction(res: any) {
    this.key = res
    console.log(this.key)

    if (this.key > 0) {
      this.closeDialog()
      this.stepper.next()


    }
    else {

      this.closeDialog()
      this.toastr.error('Invalid Employee Id', '', { positionClass: 'toast-top-right' });
      


    }

  }

 

  validate() {
    console.log(this.form2.secondCtrl.value)
    if ((this.key != this.form2.secondCtrl.value) && this.retryscount > 0) {

      let message = "Invalid Key. Retrys Left:" + this.retryscount;
      this.toastr.error("Invalid Key. Retrys Left:" + this.retryscount, '', { positionClass: 'toast-top-right' });
     this.stepper.previous()
     this.firstFormGroup.reset()
     this.secondFormGroup.reset()
     this.retryscount = this.retryscount - 1

    }
    else if ((this.key.key != this.form2.secondCtrl.value) && this.retryscount == 0) {

      this.toastr.info('Redirecting to login page', '', { positionClass: 'toast-top-right'});
      this.router.navigate(['login']);
      
    }
    else {
      this.pswddata.employeeId = this.employeeId;
      this.pswddata.password = this.form2.fourthCtrl.value;
      this.updatepswdservice.updatepswd(this.pswddata).subscribe(res => {
        let status = res;
        if (status == "Updated successfully") {
          this.pswdresetstatus = "Password Reseted Successfuly"
          console.log("Success method")
          console.log(res);
          this.stepper.next();

        }
        else {
          this.pswdresetstatus = "Something went wrong"
          console.log("Failed method")
          this.stepper.next();
        }
      },
        error => {
          this.pswdresetstatus = "Something went wrong"
          console.log("error,error", error);
          this.stepper.next();
          this.errormessage = error
        }
      );

    }

  }



  CallSpinnerDailog() {
    let dialogref = this.dailog.open(ReusabledialogComponent, { data: { showspinner: true } });

    dialogref.afterClosed().subscribe(
      (res: any) => {
        console.log("Dialog Result:", res)
      }

    )
  }
  closeDialog() {
    if (this.key >= 0) {
      this.dailog.closeAll()
    }
  }


  

}
