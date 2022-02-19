import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Movie } from 'src/app/_models/movies.model';
import { MoviesService } from 'src/app/_services/movies.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  siteImageUrl = environment.siteImageUrl;

  
  constructor(private _movies: MoviesService,
    private activatedRoute: ActivatedRoute) { }


  movies: Movie[];
  categoryName: string;
  pageNumber: number = 1;


  isSearch: boolean = false;
  ngOnInit(): void {

    window.scroll(0,0);

    this.load();
    
  }
load()
{
  this.activatedRoute.queryParams.subscribe((x) => {
    //Search Page  
    if(x['s']){
      this.isSearch = true;
      this.categoryName = "Search Results"
      
      this._movies.getSearch(x['s']).subscribe(data=> {
        this.movies = data;
      });
    }
    
    //Category
    if(x['c']){
      this.isSearch = false;
      
      //TV Series
      if(x['c'] == "tv"){

        this.categoryName = "TV";

        this._movies.getMostPopularMovies('1').subscribe(data=>{
          this.movies = data;
        })
      }
      
      //Movies most popular
      if(x['c'] == "movies" && x['t'] == "mp"){
        
        this.categoryName = "Most Popular Movies";
        this.loadPopular();
      }

      //Movies most voted
      if(x['c'] == "movies" && x['t'] == "mv"){

        this.categoryName = "Most Voted Movies";
        this.loadMostVoted();
        
      }

    }

  });

}
  loadMostVoted(){
    this._movies.getMostVotedMovies(this.pageNumber.toString()).subscribe(data=>{
      this.movies = data;
    })
  }

  loadPopular(){    
    this._movies.getMostPopularMovies(this.pageNumber.toString()).subscribe(data=>{
      this.movies = data;
    })
  }

  changePage(page: number){
    window.scroll(0,0);

    this.pageNumber = page;

    this.load();

    return false;
  }

}
