import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable, Provider} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Movie} from '../_models/movies.model';
import {ProviderMovie} from '../_models/ProviderMovie';
import {SeasonEpisode, TvDrama} from '../_models/TvDrama';


@Injectable({
  providedIn: 'root'
})
export class MoviesService {


  private baseUrl = environment.apiUrl;

  nameOfPage: BehaviorSubject<string>;

  constructor(private http: HttpClient) {


  }


  getTrendingMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(this.baseUrl + 'index/trending');
  }

  getUpcomingMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(this.baseUrl + 'index/upcoming');
  }

  getNowPlayingMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(this.baseUrl + 'index/now_playing');
  }

  getMostPopularMovies(pageNumber: string): Observable<Movie[]> {

    const params = new HttpParams()
      .set('page', pageNumber);

    return this.http.get<Movie[]>(this.baseUrl + 'index/most_popular', {params});
  }

  getMostVotedMovies(pageNumber: string): Observable<Movie[]> {

    const params = new HttpParams()
      .set('page', pageNumber);

    return this.http.get<Movie[]>(this.baseUrl + 'index/most_voted', {params});
  }

  // 464052 is a movie without providers
  // 111494 is a tv show without providers

  getMovieProvider(id: string): Observable<ProviderMovie> {
    // For testing
    // http://18.157.201.222:3000/content/508442/providers
    return this.http.get<ProviderMovie>(this.baseUrl + '/content/' + id + '/providers');
  }

  getContentById(id: string): Observable<Movie>{
    return this.http.get<Movie>(this.baseUrl + "/content/"+id);

  }

  getTvDramaById(id: string): Observable<TvDrama[]>{
    return this.http.get<TvDrama[]>(this.baseUrl + "/content/"+id+"/season");

  }

  getTvDramaSeasonById(id: string, seasonId: string): Observable<TvDrama> {
    return this.http.get<TvDrama>(this.baseUrl + '/content/' + id + '/season/' + seasonId);
  }
    
  getTvDramaSeasonAndEpisodesById(id: string, seasonId: string, episodeId: string): Observable<SeasonEpisode>{
    return this.http.get<SeasonEpisode>(this.baseUrl + "/content/"+id+"/season/"+ seasonId + "/episode/"+episodeId);
  }


  getSearch(search: string) : Observable<Movie[]>{
    return this.http.get<Movie[]>(this.baseUrl + "/content/search/" + search);
  }


  //63247 is a tv show without seasons
  //82856 is a tv show with seasons
  //67588 is a tv show in which season 16 has no episodes
  //and finally 67588 is a tv show in which season 1 has episodes
//464052 is a movie without providers
//111494 is a tv show without providers
//508442 is a movie with providers
//82856 is a tv show with providers



}
