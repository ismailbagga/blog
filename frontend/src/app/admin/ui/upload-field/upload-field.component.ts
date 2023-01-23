import { formatCurrency } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  Input,
  OnInit,
  Sanitizer,
  ViewChild,
} from '@angular/core';
import { FormControl } from '@angular/forms';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-upload-field',
  templateUrl: './upload-field.component.html',
  styleUrls: ['./upload-field.component.css'],
  // changeDetection  :  ChangeDetectionStrategy.OnPush
})
export class UploadFieldComponent implements OnInit {
  @Input('title') label = 'EMPTY TITLE';
  @Input('imageFor') imageFor = 'EMPTY IMAGE FOR ';
  @Input('maxSizeInMb') maxSizeInMb = 0;
  @Input() controler!: FormControl;
  @Input('defaulSrc') placeHolder = './assets/img-placeholder.jpg'
  @Input() ALLOWED_TYPES = ['image/png', 'image/jpg', 'image/jpeg'];
  @ViewChild('preview') previewImageRef: ElementRef | undefined;
  imgPreviewSrc: SafeResourceUrl | undefined;

  constructor(private sanitize: DomSanitizer) {}

  ngOnInit(): void {}
  fileInputChangeHandler(event: Event) {
    this.controler.markAsTouched();
    let selectedFiles = (event.target as HTMLInputElement).files;

    if (!selectedFiles || selectedFiles.length === 0) {
      this.controler && this.controler.setValue(null);
      this.removeImagePreview();
      return;
    }

    const file = selectedFiles[0];
    const { type, size, name } = file;
    if (!this.ALLOWED_TYPES.includes(type)) {
      this.controler?.setErrors({ invalidType: true });
      this.removeImagePreview();
      return;
    }
    const sizeInMb = size / (1024 * 1024);

    if (sizeInMb > this.maxSizeInMb) {
      this.controler?.setErrors({ invalidSize: true });
      this.removeImagePreview();
      return;
    }
    this.controler?.setValue(file);
    this.previewImage(file);
  }
  previewImage(file: File) {
    if (!this.previewImageRef) throw new Error('cant access preview image ');
    const imageElement = this.previewImageRef.nativeElement as HTMLImageElement;
    // `data:${file.type};base64,${file.stream}`
    const url = window.URL.createObjectURL(file);
    const safeUrl = this.sanitize.bypassSecurityTrustResourceUrl(
      window.URL.createObjectURL(file)
    );
    this.imgPreviewSrc = safeUrl;
    // imageElement.src = window.URL.createObjectURL(file);
  }
  removeImagePreview() {
    if (!this.previewImageRef) throw new Error('cant access preview image ');
    const imageElement = this.previewImageRef.nativeElement as HTMLImageElement;
    imageElement.src = './assets/img-placeholder.jpg';
  }
}
