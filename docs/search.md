# Search

Can be used to look up information about a track or artist for given search parameters.

## Endpoints offered by search service
* [Artist search](#artist-search) 
* [Track search](#track-search) 

## Artist search

Returns artist information for given parameters

**Endpoint** : `/artists/search`

**Method** : `GET`

**URL Parameters**: 

`name` artist name to search for, **required** String

**Example**
```
/artists/search?name=Kaytranada
```

### Success Response

**Code** : `200 OK`

**Response content**

`id` Artist identifier

`name` Name of found artist

**Content example**

```json
{
  "id": "6qgnBH6iDM91ipVXv28OMu",
  "name": "KAYTRANADA"
}
```

### Error Responses

`400 Bad Request` Given parameter 'name' is missing or an empty String

`404 Not Found` No artist found for given 'name' parameter



## Track search

Returns track information for track search by given parameters

**Endpoint** : `/tracks/search`

**Method** : `GET`

**URL Parameters**: 

`title` song title to search for, **required** String

`artist` name of artist for the song being searched, optional String

**Examples**
```
/tracks/search?title=Faded&artist=ZHU
/tracks/search?title=Faded
```

### Success Response

**Code** : `200 OK`

**Response content**

`title` Title of found track

`artist` Artist(s) of found track

`id` Track identifier

**Content example**

```json
{
  "artist": "KAYTRANADA, Little Dragon",
  "id": "3Il1ess0dinvEagLM3dTzG",
  "title": "BULLETS"
}
```

### Error Responses

`400 Bad Request` Given parameter 'title' is missing or an empty String

`404 Not Found` No track found for given parameters



