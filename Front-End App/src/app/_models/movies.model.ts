import { Company } from "./Company";
import { Country } from "./Country";
import { Genre } from "./Genre";
import { OriginalLanguage } from "./OriginalLanguage";

    export interface Movie {
        id: number;
        imdbId: string;
        popularity: number;
        voteCount: number;
        releaseDate: Date;
        title: string;
        voteAverage: number;
        posterPath: string;
        overview: string;
        runtime: number;
        status: string;
        contentType: string;
        adult: boolean;
        budget: number;
        revenue: number;
        inProduction?: any;
        lastAirDate?: any;
        numberOfEpisodes?: any;
        numberOfSeasons?: any;
        originalLanguage: OriginalLanguage;
        genre: Genre[];
        companies: Company[];
        countries: Country[];
    }