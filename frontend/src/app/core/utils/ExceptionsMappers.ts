import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { Pipelevel } from '../global-services/http-article.service';
export function handlingArticleUploadExceptions(
  error: Error,
  level: Pipelevel
): Error {
  if (level === 0) throw new Error('could not get access to get signature');
  if (level === 1) {
    return new Error('problem ocuure while saving image in cloudinary');
  }
  if (level === 2) {
    if (!(error instanceof HttpErrorResponse))
      return new Error('problem ocuure while saving blog info');

    if (error.status === HttpStatusCode.BadRequest)
      return new Error('some filed are missing');
    if (error.status === HttpStatusCode.Conflict)
      return new Error(error.error.message);
    if (error.status === HttpStatusCode.NotAcceptable)
      return new Error('image data does  not match image expexcted data ');
  }
  return new Error('this wierd try again pliiz :)');
}

export function handlingArticleImageEditExceptions(
  error: Error,
  level: Pipelevel
): Error {
  if (level === 0) throw new Error('could not get access to get signature');
  if (level === 1) {
    return new Error('problem ocuure while saving image in cloudinary');
  }
  if (level === 2) {
    if (!(error instanceof HttpErrorResponse))
      return new Error('problem ocuure while saving article  image');

    if (error.status === HttpStatusCode.BadRequest)
      return new Error('some filed are missing');
    if (error.status === HttpStatusCode.NotFound)
      return new Error(error.error.message);
    if (error.status === HttpStatusCode.NotAcceptable)
      return new Error('image data does  not match image expexcted data ');
  }
  return new Error('this wierd try again pliiz :)');
}
export function handlingArticleMetaDataException(error: Error) {
  if (!(error instanceof HttpErrorResponse))
    return new Error('problem ocuure while saving article  data');

  if (error.status === HttpStatusCode.BadRequest)
    return new Error('some filed are missing');
  if (error.status === HttpStatusCode.NotFound)
    return new Error(error.error.message);
  if (error.status === HttpStatusCode.Conflict)
    return new Error(error.error.message);
  return new Error('problem ocuure while saving article  data');
}
