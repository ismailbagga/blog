import {
  HttpClient,
  HttpErrorResponse,
  HttpStatusCode,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, tap } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface CloudinarySignature {
  signature: string;
  timestamp: string;
}
export const CLOUDINART_UPLOAD_URL = `https://api.cloudinary.com/v1_1/${environment.cloudName}/image/upload`;
export const CLOUDINARY_SIGNATURE = 'cloudinary-signature';

export type CloudinaryResponse = {
  signature: string;
  version: string;
  public_id: string;
};

@Injectable({
  providedIn: 'root',
})
export class ImageService {
  signatureUrl = `${environment.backendUrl}/api/v1/articles/signature`;

  constructor(private http: HttpClient) {}
  saveImage(file: File, signatureResponse: CloudinarySignature) {
    const data = new FormData();
    data.append('upload_preset', 'ml_default');
    data.append('api_key', environment.apiKey);
    data.append('timestamp', signatureResponse.timestamp);
    data.append('signature', signatureResponse.signature);
    data.append('file', file);
    return this.http.post<CloudinaryResponse>(CLOUDINART_UPLOAD_URL, data);
  }
  getSignature(forceFetching = false): Observable<CloudinarySignature> {
    const signatureJson = localStorage.getItem(CLOUDINARY_SIGNATURE);
    if (forceFetching === false && signatureJson) {
      const signature: any = JSON.parse(signatureJson);
      return of(signature);
    }
    return this.http.post<CloudinarySignature>(this.signatureUrl, {}).pipe(
      tap((value) => {
        // saving signature localy
        localStorage.setItem(CLOUDINARY_SIGNATURE, JSON.stringify(value));
      })
    );
  }
  public shouldIRefreshSignature(responseError: any) {
    const isIntance = responseError instanceof HttpErrorResponse;
    const status = HttpStatusCode.BadRequest;
    const doesStartWith =
      responseError?.error?.error?.message?.startsWith('Stale request') ??
      false;
    console.log(responseError);

    console.log(responseError?.error?.error?.message);

    return isIntance && status && doesStartWith;
  }
}
