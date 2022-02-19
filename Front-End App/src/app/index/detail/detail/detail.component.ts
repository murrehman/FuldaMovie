import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Movie } from 'src/app/_models/movies.model';
import { ProviderMovie } from 'src/app/_models/ProviderMovie';
import { Review } from 'src/app/_models/Review';
import { MoviesService } from 'src/app/_services/movies.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  siteImageUrl = environment.siteImageUrl;

  
  constructor(private _movies: MoviesService,
    private activatedRoute: ActivatedRoute) { }

  content: Movie;
  provider: ProviderMovie;
  reviews: Review[];
  filteredReviews: Review[];
  reviewComment: string;

  ngOnInit(): void {
    
    window.scroll(0,0);


    this.activatedRoute.paramMap.subscribe((x) => {
      this._movies.getContentById(x.get("id")).subscribe(data=>{
        this.content = data;

        this.updateReview();
      })

      this._movies.getMovieProvider(x.get("id")).subscribe(data=>{
        this.provider = data;
      })


    });

    
  }



  postReview(){
      
     //get from localstorage      
     this.updateReview();  

      let r = {} as Review;
      r.Message = this.reviewComment;
      r.movieId = this.content.id;
   
      if(this.reviews == null)
      {
          this.reviews = [];          
      }     

      this.reviews.push(r);
      localStorage.setItem("reviews", JSON.stringify(this.reviews));

      //get from localstorage      
      this.updateReview();      
 
      return false;
  }

  updateReview(){
    this.reviews = JSON.parse(localStorage.getItem("reviews")) as Review[];

    
    if(this.reviews != null)
    {
      this.filteredReviews = this.reviews.filter(m=> m.movieId == this.content.id);
    }

  }

}
