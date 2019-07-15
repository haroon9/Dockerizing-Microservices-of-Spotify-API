# Charts

Returns popular tracks for a given artist.

## Endpoint

**Endpoint** : `/charts/{artist-id}`

**Method** : `GET`

**URL/Path Parameter**: 

`{artist-id}` Identifier of artist to obtain popular tracks for, **required** String

**Example**
```
/charts/6qgnBH6iDM91ipVXv28OMu
```

### Success Response

**Code** : `200 OK`

**Response content**

Array with track items, containing the following fields:

`title` Title of track

`artist` Artist(s) of track

`id` Track identifier

**Content example**

```json
[{
    "artist": "KAYTRANADA, Shay Lia",
    "id": "439X8jGytErRiPnaoUJHju",
    "title": "CHANCES"
}, { ... }]
```

### Error Responses

`400 Bad Request` The given artist id was invalid

`404 Not Found` No artist information or tracks were found for the given id



