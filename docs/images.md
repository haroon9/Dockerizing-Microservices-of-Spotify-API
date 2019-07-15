# Charts

Returns cover art for a given track.

## Endpoint

**Endpoint** : `/covers/{track-id}`

**Method** : `GET`

**URL/Path Parameter**: 

`{track-id}` Identifier of track to obtain cover-art for, **required** String

`desiredWidth` Desired (whole-numbered) pixel width of cover art, in case multiple images are available, optional Integer

If a `desiredWidth`-value is supplied, the service returns the cover art closest in pixel width. If no value is supplied, the most average size found is returned.

**Example**
```
/covers/57tzAvfPHXHzCHUNp9AUBm
/covers/57tzAvfPHXHzCHUNp9AUBm?desiredWidth=600
```

### Success Response

**Code** : `200 OK`

**Response content**

`url` URL location of cover art

**Content example**

```json
{
  "url" : "https://i.scdn.co/image/0ab2f3ca824799684da80e1b3813721f6deb0617"
}
```

### Error Responses

`400 Bad Request` A given parameter was invalid

`404 Not Found` No track information or cover art was found for the given id



