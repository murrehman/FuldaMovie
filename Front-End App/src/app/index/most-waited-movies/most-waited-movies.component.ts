import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Movie } from 'src/app/_models/movies.model';
import { MoviesService } from 'src/app/_services/movies.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-most-waited-movies',
  templateUrl: './most-waited-movies.component.html',
  styleUrls: ['./most-waited-movies.component.css']
})
export class MostWaitedMoviesComponent implements OnInit {

  constructor(private _movies: MoviesService,
    private router: Router,
    private route: ActivatedRoute,) { }

    mostVotedMovies: Movie[];
  siteImageUrl = environment.siteImageUrl;

  ngOnInit(): void {
    //Getting Most Voted Movies
    this._movies.getMostVotedMovies("1").subscribe(data=> {
      this.mostVotedMovies = data.slice(0,6);
    });
  }

  
  redirectTo(uri:string){
    
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(()=>
    this.router.navigate([uri]));
  }
  

}
