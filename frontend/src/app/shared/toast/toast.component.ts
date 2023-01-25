import {
  AfterContentInit,
  AfterViewInit,
  ChangeDetectionStrategy,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
export type ToastType = 'WARN' | 'ERROR' | 'INFO' | 'SUCCESS';
@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ToastComponent implements OnInit, AfterViewInit {
  @Output() close = new EventEmitter();
  @ViewChild('wrapper') wrapperRef!: ElementRef;
  @Input() type: ToastType = 'ERROR';
  constructor() {}
  ngAfterViewInit(): void {
    const wraperElm = this.wrapperRef.nativeElement as HTMLDivElement;
    setTimeout(() => wraperElm.classList.add('movedown'), 500);
  }
  ngAfterContentInit(): void {}

  ngOnInit(): void {}
}
