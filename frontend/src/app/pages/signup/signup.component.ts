import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { throwError } from "rxjs";
import { User } from "../../models/User";
import { SignupService } from 'src/app/services/signup/signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  user: User;
  signupForm: FormGroup;
  createdAccount: Boolean = false

  constructor(

    private formBuilder: FormBuilder,
    private signupService: SignupService


  ) { }
  ngOnInit(): void {


    this.user = {
      username: "",
      password: "",
      email: "",
    };

    this.signupForm = this.formBuilder.group({
      username: ["", Validators.required],
      password: ["", Validators.required],
      email: ["", Validators.required],
    });
  }
  get f() {
    return this.signupForm.controls;
  }
  signup(): void {
    this.user = {
      username: this.f.username.value,
      password: this.f.password.value,
      email: this.f.email.value,
    };

    this.signupService.signup(this.user);

    this.createdAccount = true
  }
}



