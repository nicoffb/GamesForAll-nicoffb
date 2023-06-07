import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UserService } from 'src/app/services/user.service';
import { UtilService } from 'src/app/reutilizable/util.service';
import { CreateUserRequest } from 'src/app/models/request-dtos/create-user-request.interface';
import { EditUserRequest } from 'src/app/models/request-dtos/edit-user-request.interface';
import { UserResponse } from 'src/app/models/response-dtos/create-user-response.interface';
@Component({
  selector: 'app-modal-usuario',
  templateUrl: './modal-usuario.component.html',
  styleUrls: ['./modal-usuario.component.css']
})
export class ModalUsuarioComponent {

  createUserForm: FormGroup;
  ocultarPassword: boolean = true;
  ocultarVerifyPassword: boolean =true;
  tituloAccion: string = "Agregar";
  botonAccion: string = "Guardar";

  constructor(
    private modalActual: MatDialogRef<ModalUsuarioComponent>,
    @Inject(MAT_DIALOG_DATA) public datosUsuario: UserResponse,
    private fb: FormBuilder,
    private _userService: UserService,
    private _utilService: UtilService
  ) {
    this.createUserForm = this.fb.group({
      username: ["", Validators.required],
      avatar: ["", Validators.required],
      fullName: ["", Validators.required],
      password:[""],
      verifyPassword:[""]

    });




  }

  ngOnInit(): void {
    if (this.datosUsuario != null) {
      this.tituloAccion = "Editar";
      this.botonAccion = "Actualizar";
    }
    console.log(this.datosUsuario)
    console.log(this.tituloAccion)
    if (this.datosUsuario != null) {

      this.createUserForm.patchValue({
        username: this.datosUsuario.username,
        avatar: this.datosUsuario.avatar,
        fullName: this.datosUsuario.fullName
      });
    }
  }

  guardarEditar_Usuario() {



    const _usuario: CreateUserRequest = {
      username: this.createUserForm.value.username,
      password: this.createUserForm.value.password,
      verifyPassword: this.createUserForm.value.verifyPassword,
      avatar: this.createUserForm.value.avatar,
      fullName: this.createUserForm.value.fullName
    };

    console.log(this.datosUsuario)
    if (this.datosUsuario == null) {

      this._userService.registrarUsuario(_usuario).subscribe({
        next: (data) => {
          if (data) {
            this._utilService.mostrarAlerta("El usuario fue registrado", "Éxito");
            this.modalActual.close("true");
          } else {
            this._utilService.mostrarAlerta("No se pudo crear el usuario", "Error");
          }
        },
        error: (e) => { }
      });
    } else {

      const _usuarioEdit: EditUserRequest = {
        id: this.datosUsuario.id,
        avatar: this.createUserForm.value.avatar,
        fullName: this.createUserForm.value.fullName
      };

      this._userService.editar(_usuarioEdit).subscribe({
        next: (data) => {
          if (data) {
            this._utilService.mostrarAlerta("El usuario fue editado", "Éxito");
            this.modalActual.close("true");
          } else {
            this._utilService.mostrarAlerta("No se pudo editar el usuario", "Error");
          }
        },
        error: (e) => { }
      });
    }
  }

}









