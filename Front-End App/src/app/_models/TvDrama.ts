import { Company } from "./Company";
import { Country } from "./Country";
import { Genre } from "./Genre";
import { OriginalLanguage } from "./OriginalLanguage";

    export interface Tv {
        id: number;
        imdbId?: any;
        popularity: number;
        voteCount: number;
        releaseDate: Date;
        title: string;
        voteAverage: number;
        posterPath: string;
        overview: string;
        runtime?: any;
        status: string;
        contentType: string;
        adult?: any;
        budget?: any;
        revenue?: any;
        inProduction: boolean;
        lastAirDate: Date;
        numberOfEpisodes: number;
        numberOfSeasons: number;
        originalLanguage: OriginalLanguage;
        genre: Genre[];
        companies: Company[];
        countries: Country[];
    }

    export interface TvDrama {
        airDate: Date;
        episodeCount: number;
        id: number;
        name: string;
        overview: string;
        posterPath: string;
        seasonNumber: number;
        tv: Tv;
    }
    
    export interface SeasonEpisode {
            airDate: Date;
            episodeNumber: number;
            name: string;
            overview: string;
            id: number;
            productionCode: string;
            seasonNumber: number;
            voteAverage: number;
            voteCount: number;
            imdbId?: any;
            season: TvDrama;
        }
    
    
    
    