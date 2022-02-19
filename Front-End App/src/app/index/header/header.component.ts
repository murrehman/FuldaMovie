import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MoviesService } from 'src/app/_services/movies.service';

declare function hideSearchModel(): any;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {


  searchInput: string;


  constructor(private router: Router, private _movies: MoviesService) {
  }

  ngOnInit(): void {

  }

  search(){
    
    
    this.router.navigate(['/index/search'], { queryParams: { s: this.searchInput } });
    hideSearchModel();
  }

}



