import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'tableFilter'
})
export class TableFilterPipe implements PipeTransform {

  transform(li: any[], value: string): any {
   return value !== undefined && li !== undefined ? li.filter(val =>
      val.title.indexOf(value) !== -1 ||

      val.description.indexOf(value) !== -1 ||

      val.usern.indexOf(value) !== -1 ||

      val.title.toLowerCase().indexOf(value.toLowerCase()) !== -1 ||

      val.description.toLowerCase().indexOf(value.toLowerCase()) !== -1 ||

      val.usern.toLowerCase().indexOf(value.toLowerCase()) !== -1
      
    ) : li;
  }
}
