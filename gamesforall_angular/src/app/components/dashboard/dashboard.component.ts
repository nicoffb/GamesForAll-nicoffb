import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Category } from 'src/app/models/category.interface';
import { UserResponse } from 'src/app/models/user.interface';
import { CategoryService } from 'src/app/services/category.service';
import { UserService } from 'src/app/services/user.service';
import { UtilService } from 'src/app/services/util.service';

import Swal from 'sweetalert2';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  user: UserResponse | null = null; 

  constructor(private utilService: UtilService) { }

  ngOnInit(): void {
    this.utilService.getLoggedUser().subscribe(
      (data: UserResponse) => {
        this.user = data;
      },
      (error: any) => {
        console.error(error);
      }
    );
  }

}

