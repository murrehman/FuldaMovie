import { Component, ɵCompiler_compileModuleAndAllComponentsAsync__POST_R3__ } from '@angular/core';

declare function InitializeJqueryCodeInMainJS(): any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'movieApp';


  ngAfterViewChecked()
  {
    InitializeJqueryCodeInMainJS();
  }

}
