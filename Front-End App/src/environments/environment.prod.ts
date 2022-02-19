export const environment = {
  production: false,

  apiUrl: 'http://localhost:3000/',
  siteImageUrl: 'https://image.tmdb.org/t/p/original',
  frontendUrl: 'http://localhost:4200/',

  keycloakConfig: {
    url: 'http://localhost:9990/auth',
    realm: 'fuldaMovie',
    clientId: 'spa-fuldaMovie',
  }
};
