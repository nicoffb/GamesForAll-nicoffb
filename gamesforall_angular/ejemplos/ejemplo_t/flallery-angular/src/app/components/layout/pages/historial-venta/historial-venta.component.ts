import { Component, OnInit } from '@angular/core';
import * as XLSX from 'xlsx';

import { MAT_DATE_FORMATS } from '@angular/material/core';
import { VentaService } from 'src/app/services/venta.service';
import { Venta, VentaResponse } from 'src/app/models/response-dtos/venta-response.interface';
import { PageEvent } from '@angular/material/paginator';

export const MY_DATA_FORMATS={
  parse:{
    dateInput: 'DD/MM/YYYY'
  },
  display:{
    dateInput: 'DD/MM/YYYY',
    monthYearLabel:'MMMM YYYY'
  }
}

@Component({
  selector: 'app-historial-venta',
  templateUrl: './historial-venta.component.html',
  styleUrls: ['./historial-venta.component.css']
})
export class HistorialVentaComponent implements OnInit {
  ventaDataList: VentaResponse[] = [];
  columnasTable: string[] = ['artworkName', 'precio', 'fechaVenta', 'usernameComprador', 'usernameVendedor'];
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  searchParam = '';

  constructor(private ventaService: VentaService) {}

  ngOnInit(): void {
    this.getArtworkSales();
  }

  getArtworkSales(): void {
    this.ventaService.getListaVentas(this.currentPage, this.pageSize,this.searchParam).subscribe(response => {
      this.ventaDataList = response.content;
      this.totalElements = response.totalElements;
    });
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getArtworkSales();
  }

  onSearch(): void {
    this.currentPage = 0;
    this.getArtworkSales();
  }

  exportToExcel(): void {
    const wb = XLSX.utils.book_new();
    const ws = XLSX.utils.json_to_sheet(this.ventaDataList)

    XLSX.utils.book_append_sheet(wb,ws,"Historial");
    XLSX.writeFile(wb,"Historial Ventas.xlsx")
  }
}

