import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';


import { UserService } from 'src/app/services/user.service';

import Swal from 'sweetalert2';
import { UserResponse } from 'src/app/models/user.interface';
import { UtilService } from 'src/app/services/util.service';
import { ModalUsuarioComponent } from '../modal-usuario/modal-usuario.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit/*, AfterViewInit*/ {

  columnasTable: string[] = ['id', 'username', 'fullName', 'createdAt','ediciones', 'acciones'];

  user: UserResponse | null = null;
  dataInicio: UserResponse[] = [];
  dataListaUsuarios = new MatTableDataSource(this.dataInicio);
  @ViewChild(MatPaginator) paginacionTabla!: MatPaginator;

  constructor(
    private dialog: MatDialog,
    private _usuarioService: UserService,
    private _utilService: UtilService
  ) { }


  obtenerUsuarios() {
    this._usuarioService.lista().subscribe({
      next: (data) => {
        if (data)
          this.dataListaUsuarios.data = data.filter(data => data.username != this.user?.username);
        else
          this._utilService.mostrarAlerta("No se encontraron datos", "Error")
      },
      error: (e) => { }
    })
  }


  ngOnInit(): void {

    this.obtenerUsuarios();
  }


  ngAfterViewInit(): void {
    this.dataListaUsuarios.paginator = this.paginacionTabla;
  }


  aplicarFiltroTabla(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataListaUsuarios.filter = filterValue.trim().toLocaleLowerCase();
  }


  nuevoUsuario() {
    this.dialog.open(ModalUsuarioComponent, {
      disableClose: true
    }).afterClosed().subscribe(res => {
      if (res == "true") this.obtenerUsuarios();
    });
  }


  editUsuario(userResponse: UserResponse) {
    this.dialog.open(ModalUsuarioComponent, {
      disableClose: true,
      data: userResponse
    }).afterClosed().subscribe(res => {
      if (res == "true") this.obtenerUsuarios();
    });
  }


  eliminarUsuario(userResponse: UserResponse) {
    Swal.fire({
      title: '¿Seguro que quiere eliminar el usuario?',
      text: userResponse.fullName,
      icon: "warning",
      confirmButtonColor: '#3085d6',
      confirmButtonText: "Si, eliminar",
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'No, volver'
    }).then((res) => {
      if (res.isConfirmed) {

        this._usuarioService.eliminar(userResponse.id).subscribe({
          next: (data) => {
            this._utilService.mostrarAlerta("El usuario ha sido eliminado", "Hecho");
            this.obtenerUsuarios();
          },
        })

      }
    })
  }


//   cambiarRolUsuario(userResponse: UserResponse) {
//     Swal.fire({
//       title: `Desea hacer ${userResponse.role=='Admin'?'Usuario':'Administrador'} a ${userResponse.fullName}?`,
//       text: userResponse.fullName,
//       icon: "warning",
//       confirmButtonColor: '#3085d6',
//       confirmButtonText: "Si, cambiar rol",
//       showCancelButton: true,
//       cancelButtonColor: '#d33',
//       cancelButtonText: 'No, volver'
//     }).then((res) => {
//       if (res.isConfirmed) {

//         this._usuarioService.cambiarRol(userResponse.id).subscribe({
//           next: (data) => {
//             this._utilService.mostrarAlerta("se cambió el rol correctamente", "Listo!");
//             this.obtenerUsuarios();
//           },
//         })

//       }
//     })
//   }


//   banearUsuario(userResponse: UserResponse) {
//     Swal.fire({
//       title: '¿Desea banear el usuario?',
//       text: userResponse.fullName,
//       icon: "warning",
//       confirmButtonColor: '#3085d6',
//       confirmButtonText: "Si, banear",
//       showCancelButton: true,
//       cancelButtonColor: '#d33',
//       cancelButtonText: 'No, volver'
//     }).then((res) => {
//       if (res.isConfirmed) {

//         this._usuarioService.cambiarEstadoBaneoUsuario(userResponse.id).subscribe({
//           next: (data) => {
//             this._utilService.mostrarAlerta("El usuario fue baneado", "Listo!");
//             this.obtenerUsuarios();
//           },
//         })

//       }
//     })
//   }


//   desBanearUsuario(userResponse: UserResponse) {
//     Swal.fire({
//       title: '¿Desea desbanear el usuario?',
//       text: userResponse.fullName,
//       icon: "warning",
//       confirmButtonColor: '#3085d6',
//       confirmButtonText: "Si, desbanear",
//       showCancelButton: true,
//       cancelButtonColor: '#d33',
//       cancelButtonText: 'No, volver'
//     }).then((res) => {
//       if (res.isConfirmed) {

//         this._usuarioService.cambiarEstadoBaneoUsuario(userResponse.id).subscribe({
//           next: (data) => {
//             this._utilService.mostrarAlerta("El usuario fue desbaneado", "Listo!");
//             this.obtenerUsuarios();
//           },
//         })

//       }
//     })
//   }

}
