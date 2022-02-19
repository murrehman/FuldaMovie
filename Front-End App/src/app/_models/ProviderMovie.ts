import { Country } from "./Country";


export interface Flatrate {
    display_priority: number;
    logo_path: string;
    provider_id: number;
    provider_name: string;
}


//rent are providers in which you pay and then you can watch the content for 24 hours or something
//buy is the same but once you pay you can watch the film anytime
//and flatrent are netflix , disney+ and all of this in which you can watch the content while you are paying a monthly subscription
export interface ProviderMovie {
    country: Country;
    link: string;
    rent: Flatrate[];
    buy: Flatrate[];
    flatrate: Flatrate[];
}

