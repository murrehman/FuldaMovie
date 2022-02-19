import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Movie } from 'src/app/_models/movies.model';
import { MoviesService } from 'src/app/_services/movies.service';
import { environment } from 'src/environments/environment';

declare function InitializeJqueryCodeInMainJS(boolean): any;


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private _movies: MoviesService,
    private router: Router,
    private route: ActivatedRoute,) { }


  nowPlayingMovies: Movie[];
  trendingMovies: Movie[];
  popularMovies: Movie[];
  upcomingMovies: Movie[];

  siteImageUrl = environment.siteImageUrl;

  ngOnInit(): void {

    //Getting Movies for Slider
    this._movies.getNowPlayingMovies().subscribe(data=> {
      this.nowPlayingMovies = data.slice(0,5);      
      console.log(this.nowPlayingMovies);

       setTimeout(() => {
        InitializeJqueryCodeInMainJS(true);
       }, 10);    
    

    });


    //Getting Trending Movies
    this._movies.getTrendingMovies().subscribe(data=> {
      this.trendingMovies = data.slice(0,6);
    });

    //Getting Popular Movies
    this._movies.getMostPopularMovies("1").subscribe(data=> {
      this.popularMovies = data.slice(0,6);
    });


       //Getting Upcoming Movies
       this._movies.getUpcomingMovies().subscribe(data=> {
        this.upcomingMovies = data.slice(0,6);
      });


    
  


  }

  
  ngAfterViewChecked()
  {

  }




}
