--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1 (Debian 13.1-1.pgdg100+1)
-- Dumped by pg_dump version 13.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: content; Type: SCHEMA; Schema: -; Owner: pedroars
--

CREATE DATABASE msc;
\connect msc;

CREATE SCHEMA content;
ALTER SCHEMA content OWNER TO pedroars;

CREATE SCHEMA users;
ALTER SCHEMA users OWNER TO keycloak;

--
-- Name: SCHEMA content; Type: COMMENT; Schema: -; Owner: pedroars
--

COMMENT ON SCHEMA content IS 'Schema with all the content: films, series, etc.';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Company; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."company" (
    id integer NOT NULL,
    description text,
    headquarters text,
    homepage text,
    logo_path text,
    name text
);


ALTER TABLE content."company" OWNER TO pedroars;

--
-- Name: Content; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."content" (
    id integer NOT NULL,
    imdb_id text,
    popularity numeric,
    vote_count integer,
    release_date date,
    title text,
    vote_average numeric,
    poster_path text,
    overview text,
    runtime integer,
    status text,
	adult boolean,
    budget integer,
    revenue bigint,
	in_production boolean,
    last_air_date date,
    number_of_episodes integer,
    number_of_seasons integer,
	dataType integer,
    original_language text
);


ALTER TABLE content."content" OWNER TO pedroars;

--
-- Name: Content_Company; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."content_company" (
    content_id integer NOT NULL,
    company_id integer NOT NULL
);


ALTER TABLE content."content_company" OWNER TO pedroars;

--
-- Name: Content_Country; Type: TABLE; Schema: content; Owner: postgres
--

CREATE TABLE content."content_country" (
    content_id integer NOT NULL,
    country_id text NOT NULL
);


ALTER TABLE content."content_country" OWNER TO pedroars;

--
-- Name: Content_Genre; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."content_genre" (
    content_id integer NOT NULL,
    genre_id integer NOT NULL
);


ALTER TABLE content."content_genre" OWNER TO pedroars;

--
-- Name: Country; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."country" (
    iso_3166_1 text NOT NULL,
    name text
);


ALTER TABLE content."country" OWNER TO pedroars;

--
-- Name: Episode; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."episode" (
    air_date date,
    episode_number integer,
    name text,
    overview text,
    id integer NOT NULL,
    production_code text,
    season_number integer,
    vote_average numeric,
    vote_count integer,
    imdb_id text,
    season_id integer
);


ALTER TABLE content."episode" OWNER TO pedroars;

--
-- Name: Genre; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."genre" (
    id integer NOT NULL,
    name text NOT NULL
);


ALTER TABLE content."genre" OWNER TO pedroars;

--
-- Name: Language; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."language" (
    iso_639_1 text NOT NULL,
    english_name text,
    name text
);


ALTER TABLE content."language" OWNER TO pedroars;

--
-- Name: Provider; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."provider" (
    display_priority integer,
    logo_path text,
    provider_id integer NOT NULL,
    provider_name text
);


ALTER TABLE content."provider" OWNER TO pedroars;

--
-- Name: Season; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."season" (
    air_date date,
    episode_count integer,
    id integer NOT NULL,
    name text,
    overview text,
    poster_path text,
    season_number integer,
    tv_id integer
);


ALTER TABLE content."season" OWNER TO pedroars;


--
-- Name: WatchProvider; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."watch_provider" (
    content_id integer,
    country_id text,
    link text NOT NULL
);


ALTER TABLE content."watch_provider" OWNER TO pedroars;

--
-- Name: WatchProvider_Provider_Buy; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."watch_provider_provider_buy" (
    link text NOT NULL,
    provider_id integer NOT NULL
);


ALTER TABLE content."watch_provider_provider_buy" OWNER TO pedroars;

--
-- Name: WatchProvider_Provider_Flatrent; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."watch_provider_provider_flatrent" (
    link text NOT NULL,
    provider_id integer NOT NULL
);


ALTER TABLE content."watch_provider_provider_flatrent" OWNER TO pedroars;

--
-- Name: WatchProvider_Provider_Rent; Type: TABLE; Schema: content; Owner: pedroars
--

CREATE TABLE content."watch_provider_provider_rent" (
    link text NOT NULL,
    provider_id integer NOT NULL
);


ALTER TABLE content."watch_provider_provider_rent" OWNER TO pedroars;

--
-- Name: Company Companies_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."company"
    ADD CONSTRAINT "companies_pkey" PRIMARY KEY (id);


--
-- Name: Content_Country Content_Country_pkey; Type: CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content."content_country"
    ADD CONSTRAINT "Content_Country_pkey" PRIMARY KEY (content_id, country_id);


--
-- Name: Content_Genre Content_Genre_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."content_genre"
    ADD CONSTRAINT "Content_Genre_pkey" PRIMARY KEY (content_id, genre_id);


--
-- Name: Content Content_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."content"
    ADD CONSTRAINT "Content_pkey" PRIMARY KEY (id);


--
-- Name: Country Countries_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."country"
    ADD CONSTRAINT "Countries_pkey" PRIMARY KEY (iso_3166_1);


--
-- Name: Episode EPISODE_IMDB_ID_UK; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."episode"
    ADD CONSTRAINT "EPISODE_IMDB_ID_UK" UNIQUE (imdb_id);


--
-- Name: Episode Episode_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."episode"
    ADD CONSTRAINT "Episode_pkey" PRIMARY KEY (id);

--
-- Name: Genre Genre_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."genre"
    ADD CONSTRAINT "Genre_pkey" PRIMARY KEY (id);


--
-- Name: Language Languages_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."language"
    ADD CONSTRAINT "Languages_pkey" PRIMARY KEY (iso_639_1);


--
-- Name: Provider Provider_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."provider"
    ADD CONSTRAINT "Provider_pkey" PRIMARY KEY (provider_id);


--
-- Name: Season Season_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."season"
    ADD CONSTRAINT "Season_pkey" PRIMARY KEY (id);



--
-- Name: WatchProvider_Provider_Buy WatchProvider_Provider_buy_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_buy"
    ADD CONSTRAINT "WatchProvider_Provider_buy_pkey" PRIMARY KEY (link, provider_id);


--
-- Name: WatchProvider_Provider_Flatrent WatchProvider_Provider_flatrent_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_flatrent"
    ADD CONSTRAINT "WatchProvider_Provider_flatrent_pkey" PRIMARY KEY (link, provider_id);


--
-- Name: WatchProvider_Provider_Rent WatchProvider_Provider_rent_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_rent"
    ADD CONSTRAINT "WatchProvider_Provider_rent_pkey" PRIMARY KEY (link, provider_id);


--
-- Name: WatchProvider WatchProvider_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider"
    ADD CONSTRAINT "WatchProvider_pkey" PRIMARY KEY (link);


--
-- Name: Content_Company content_companies_pkey; Type: CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."content_company"
    ADD CONSTRAINT content_companies_pkey PRIMARY KEY (content_id, company_id);


--
-- Name: Content_Company content_company_company_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."content_company"
    ADD CONSTRAINT content_company_company_fk FOREIGN KEY (company_id) REFERENCES content."company"(id);


--
-- Name: Content_Company content_company_content_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."content_company"
    ADD CONSTRAINT content_company_content_fk FOREIGN KEY (content_id) REFERENCES content."content"(id);


--
-- Name: Content_Country content_country_content_fk; Type: FK CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content."content_country"
    ADD CONSTRAINT content_country_content_fk FOREIGN KEY (content_id) REFERENCES content."content"(id);


--
-- Name: Content_Country content_country_country_fk; Type: FK CONSTRAINT; Schema: content; Owner: postgres
--

ALTER TABLE ONLY content."content_country"
    ADD CONSTRAINT content_country_country_fk FOREIGN KEY (country_id) REFERENCES content."country"(iso_3166_1);


--
-- Name: Content_Genre content_genre_content_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."content_genre"
    ADD CONSTRAINT content_genre_content_fk FOREIGN KEY (content_id) REFERENCES content."content"(id);


--
-- Name: Content_Genre content_genre_genre_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."content_genre"
    ADD CONSTRAINT content_genre_genre_fk FOREIGN KEY (genre_id) REFERENCES content."genre"(id);


--
-- Name: Episode episode_season_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."episode"
    ADD CONSTRAINT episode_season_fk FOREIGN KEY (season_id) REFERENCES content."season"(id) NOT VALID;


--
-- Name: Content original_language_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."content"
    ADD CONSTRAINT original_language_fk FOREIGN KEY (original_language) REFERENCES content."language"(iso_639_1);


--
-- Name: Season season_tv_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."season"
    ADD CONSTRAINT season_tv_fk FOREIGN KEY (tv_id) REFERENCES content."content"(id) NOT VALID;


--
-- Name: WatchProvider watchprovider_country_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider"
    ADD CONSTRAINT watchprovider_country_fk FOREIGN KEY (country_id) REFERENCES content."country"(iso_3166_1);

--
-- Name: WatchProvider watchprovider_content_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider"
    ADD CONSTRAINT watchprovider_content_fk FOREIGN KEY (content_id) REFERENCES content."content"(id);


--
-- Name: WatchProvider_Provider_Buy watchprovider_provider_buy_provider_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_buy"
    ADD CONSTRAINT watchprovider_provider_buy_provider_fk FOREIGN KEY (provider_id) REFERENCES content."provider"(provider_id);


--
-- Name: WatchProvider_Provider_Buy watchprovider_provider_buy_watchproviderfk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_buy"
    ADD CONSTRAINT watchprovider_provider_buy_watchproviderfk FOREIGN KEY (link) REFERENCES content."watch_provider"(link);


--
-- Name: WatchProvider_Provider_Flatrent watchprovider_provider_flatrent_provider_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_flatrent"
    ADD CONSTRAINT watchprovider_provider_flatrent_provider_fk FOREIGN KEY (provider_id) REFERENCES content."provider"(provider_id);


--
-- Name: WatchProvider_Provider_Flatrent watchprovider_provider_flatrent_watchprovider_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_flatrent"
    ADD CONSTRAINT watchprovider_provider_flatrent_watchprovider_fk FOREIGN KEY (link) REFERENCES content."watch_provider"(link);


--
-- Name: WatchProvider_Provider_Rent watchprovider_provider_rent_provider_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_rent"
    ADD CONSTRAINT watchprovider_provider_rent_provider_fk FOREIGN KEY (provider_id) REFERENCES content."provider"(provider_id);


--
-- Name: WatchProvider_Provider_Rent watchprovider_provider_rent_watchprovider_fk; Type: FK CONSTRAINT; Schema: content; Owner: pedroars
--

ALTER TABLE ONLY content."watch_provider_provider_rent"
    ADD CONSTRAINT watchprovider_provider_rent_watchprovider_fk FOREIGN KEY (link) REFERENCES content."watch_provider"(link);


--
-- Name: SCHEMA content; Type: ACL; Schema: -; Owner: pedroars
--

GRANT ALL ON SCHEMA content TO arsalanshahid;
GRANT ALL ON SCHEMA content TO muhammadadnan;
GRANT ALL ON SCHEMA content TO muteeurrehman;
GRANT ALL ON SCHEMA content TO shazeemashar;
GRANT ALL ON SCHEMA content TO production;


--
-- Name: TABLE "Company"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."company" TO arsalanshahid;
GRANT ALL ON TABLE content."company" TO muhammadadnan;
GRANT ALL ON TABLE content."company" TO shazeemashar;
GRANT ALL ON TABLE content."company" TO muteeurrehman;
GRANT ALL ON TABLE content."company" TO production;


--
-- Name: TABLE "Content"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."content" TO arsalanshahid;
GRANT ALL ON TABLE content."content" TO muhammadadnan;
GRANT ALL ON TABLE content."content" TO shazeemashar;
GRANT ALL ON TABLE content."content" TO muteeurrehman;
GRANT ALL ON TABLE content."content" TO production;

--
-- Name: TABLE "Content_Company"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."content_company" TO arsalanshahid;
GRANT ALL ON TABLE content."content_company" TO muhammadadnan;
GRANT ALL ON TABLE content."content_company" TO shazeemashar;
GRANT ALL ON TABLE content."content_company" TO muteeurrehman;
GRANT ALL ON TABLE content."content_company" TO production;

--
-- Name: TABLE "Content_Country"; Type: ACL; Schema: content; Owner: postgres
--

GRANT ALL ON TABLE content."content_country" TO pedroars;
GRANT ALL ON TABLE content."content_country" TO arsalanshahid;
GRANT ALL ON TABLE content."content_country" TO muhammadadnan;
GRANT ALL ON TABLE content."content_country" TO shazeemashar;
GRANT ALL ON TABLE content."content_country" TO muteeurrehman;
GRANT ALL ON TABLE content."content_country" TO production;

--
-- Name: TABLE "Content_Genre"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."content_genre" TO arsalanshahid;
GRANT ALL ON TABLE content."content_genre" TO muhammadadnan;
GRANT ALL ON TABLE content."content_genre" TO shazeemashar;
GRANT ALL ON TABLE content."content_genre" TO muteeurrehman;
GRANT ALL ON TABLE content."content_genre" TO production;

--
-- Name: TABLE "Country"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."country" TO arsalanshahid;
GRANT ALL ON TABLE content."country" TO muhammadadnan;
GRANT ALL ON TABLE content."country" TO shazeemashar;
GRANT ALL ON TABLE content."country" TO muteeurrehman;
GRANT ALL ON TABLE content."country" TO production;

--
-- Name: TABLE "Episode"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."episode" TO arsalanshahid;
GRANT ALL ON TABLE content."episode" TO muhammadadnan;
GRANT ALL ON TABLE content."episode" TO shazeemashar;
GRANT ALL ON TABLE content."episode" TO muteeurrehman;
GRANT ALL ON TABLE content."episode" TO production;


--
-- Name: TABLE "Genre"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."genre" TO arsalanshahid;
GRANT ALL ON TABLE content."genre" TO muhammadadnan;
GRANT ALL ON TABLE content."genre" TO shazeemashar;
GRANT ALL ON TABLE content."genre" TO muteeurrehman;
GRANT ALL ON TABLE content."genre" TO production;

--
-- Name: TABLE "Language"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."language" TO arsalanshahid;
GRANT ALL ON TABLE content."language" TO muhammadadnan;
GRANT ALL ON TABLE content."language" TO shazeemashar;
GRANT ALL ON TABLE content."language" TO muteeurrehman;
GRANT ALL ON TABLE content."language" TO production;

--
-- Name: TABLE "Provider"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."provider" TO arsalanshahid;
GRANT ALL ON TABLE content."provider" TO muhammadadnan;
GRANT ALL ON TABLE content."provider" TO shazeemashar;
GRANT ALL ON TABLE content."provider" TO muteeurrehman;
GRANT ALL ON TABLE content."provider" TO production;

--
-- Name: TABLE "Season"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."season" TO arsalanshahid;
GRANT ALL ON TABLE content."season" TO muhammadadnan;
GRANT ALL ON TABLE content."season" TO shazeemashar;
GRANT ALL ON TABLE content."season" TO muteeurrehman;
GRANT ALL ON TABLE content."season" TO production;


--
-- Name: TABLE "WatchProvider"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."watch_provider" TO arsalanshahid;
GRANT ALL ON TABLE content."watch_provider" TO muhammadadnan;
GRANT ALL ON TABLE content."watch_provider" TO shazeemashar;
GRANT ALL ON TABLE content."watch_provider" TO muteeurrehman;
GRANT ALL ON TABLE content."watch_provider" TO production;

--
-- Name: TABLE "WatchProvider_Provider_Buy"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."watch_provider_provider_buy" TO arsalanshahid;
GRANT ALL ON TABLE content."watch_provider_provider_buy" TO muhammadadnan;
GRANT ALL ON TABLE content."watch_provider_provider_buy" TO shazeemashar;
GRANT ALL ON TABLE content."watch_provider_provider_buy" TO muteeurrehman;
GRANT ALL ON TABLE content."watch_provider_provider_buy" TO production;

--
-- Name: TABLE "WatchProvider_Provider_Flatrent"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."watch_provider_provider_flatrent" TO arsalanshahid;
GRANT ALL ON TABLE content."watch_provider_provider_flatrent" TO muhammadadnan;
GRANT ALL ON TABLE content."watch_provider_provider_flatrent" TO shazeemashar;
GRANT ALL ON TABLE content."watch_provider_provider_flatrent" TO muteeurrehman;
GRANT ALL ON TABLE content."watch_provider_provider_flatrent" TO production;

--
-- Name: TABLE "WatchProvider_Provider_Rent"; Type: ACL; Schema: content; Owner: pedroars
--

GRANT ALL ON TABLE content."watch_provider_provider_rent" TO arsalanshahid;
GRANT ALL ON TABLE content."watch_provider_provider_rent" TO muhammadadnan;
GRANT ALL ON TABLE content."watch_provider_provider_rent" TO shazeemashar;
GRANT ALL ON TABLE content."watch_provider_provider_rent" TO muteeurrehman;
GRANT ALL ON TABLE content."watch_provider_provider_rent" TO production;

--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: content; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA content REVOKE ALL ON TABLES  FROM postgres;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA content GRANT ALL ON TABLES  TO pedroars;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA content GRANT ALL ON TABLES  TO arsalanshahid;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA content GRANT ALL ON TABLES  TO muhammadadnan;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA content GRANT ALL ON TABLES  TO shazeemashar;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA content GRANT ALL ON TABLES  TO muteeurrehman;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA content GRANT ALL ON TABLES  TO production;

--
-- PostgreSQL database dump complete
--

