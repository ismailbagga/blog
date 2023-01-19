import {
  AfterContentInit,
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { Subscription, fromEvent } from 'rxjs';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styles: [],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SideBarComponent implements OnInit, AfterViewInit, OnChanges {
  isSideNavVisible = false;
  @Input('isLightTheme') isLightTheme: boolean | null = null;
  @ViewChild('svg') svgRef!: ElementRef<SVGElement>;
  subscription?: Subscription;

  constructor(private cdRef: ChangeDetectorRef) {}
  ngOnChanges(changes: SimpleChanges): void {
    const themeChanges = changes['isLightTheme'];
    if (themeChanges && themeChanges.firstChange) return;
    this.changeBarIconColor(true);
  }

  ngAfterViewInit(): void {
    this.changeBarIconColor(true);
  }
  changeBarIconColor(onInit = false) {
    const svgElm = this.svgRef.nativeElement;
    if (this.isLightTheme) svgElm.setAttribute('fill', '#161A1D');
    else svgElm.setAttribute('fill', '#ffffff');
  }
  ngOnInit(): void {}
  toggleSideNav() {
    console.log('toggleSideNav');

    this.isSideNavVisible = !this.isSideNavVisible;
    if (this.isSideNavVisible) {
      document.body.style.overflowY = 'hidden';
      this.trackWindowResizing();
    } else {
      this.untrackWindowResizing();
      document.body.style.overflowY = 'hidden';
    }
  }
  @HostListener('window.resier')
  onResizing() {}
  trackWindowResizing() {
    this.subscription = fromEvent(window, 'resize').subscribe((resizeEvent) => {
      console.log('windows Size Changes', resizeEvent);
      const screenWidth = document.documentElement.clientWidth;
      console.log(screenWidth);
      if (screenWidth > 640) {
        this.toggleSideNav();
        this.cdRef.detectChanges();
      }
    });
  }
  untrackWindowResizing() {
    this.subscription && this.subscription.unsubscribe();
  }
}
