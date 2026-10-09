#include <avr/io.h>
#include <avr/portpins.h>
#include <avr/iom16.h>
#include <avr/interrupt.h>
#include <stdio.h>
#include <avr/iom16.h>
#include <avr/sleep.h>
#include <inttypes.h>



#define sbi(a, B)  (a |= (1<< B))
#define cbi(a, B)  (a &= ~(1 << B))
#define set_bits(a, B)  (a |= (1 << B))
#define clear_bits(a, B)  (a &= ~(1 << B))




#define CHECKBIT(x,b) x&b
#define SETBIT(x,b) x|=b;
#define CLEARBIT(x,b) x&=~b;
#define TOGGLEBIT(x,b) x^=b;

unsigned char dato;
unsigned char dato2;
int adcval;
int timer1st;
#define F_OSC 8000000		           /* oscillator-frequency in Hz */
#define UART_BAUD_RATE 38400
//#define UART_BAUD_CALC(UART_BAUD_RATE,F_OSC) ((F_OSC)/((UART_BAUD_RATE)*16)-1)
#define UART_BAUD_CALC(UART_BAUD_RATE,F_OSC) ((F_OSC)/((UART_BAUD_RATE)*16)-1)

//#define UART_BAUD_CALC(UART_BAUD_RATE,F_OSC) F_OSC/UART_BAUD_RATE/8-1

//#define UART_BAUD_CALC F_OSC/UART_BAUD_RATE/8-1


//#define ICP PIND4
int owrtm1=0;
int ICP=0;

int twave=0;
int ttemp=0;
//define ovrflow counter
uint16_t ov_counter1;
uint16_t ov_counter0;
uint16_t ov_counter2;
//define times for start and end of signal
uint16_t rising, falling;
//define overall counts
uint32_t counts;
//overflow counter interrupts service routine
ISR(TIMER0_OVF_vect)
{
  if(ov_counter0==4096)
    {
  if(twave==0){
    //sbi(PORTD,PD4);
    ttemp=1;	
    //sbi(ADCSRA,6);
    
    timer1st=1;   


  }
  else
    {
      
      // cbi(PORTD,PD4);
      ttemp=0;
      timer1st=0;
    }
      ov_counter0=0;
    }
  ov_counter0++;
}

ISR(TIMER2_OVF_vect)
{
  if(ov_counter2==122)
    {
  if(twave==0){
    //    sbi(PORTD,PD5);
    twave=1;	
    //    sbi(ADCSRA,6);
  }
  else
    {
      //cbi(PORTD,PD5);
      twave=0;
      
    }
      ov_counter2=0;
    }
  ov_counter2++;
}

ISR(TIMER1_OVF_vect){
  // t1=1/65535
  //fs=TCNT1=65535-64511=1024

  if(owrtm1==1)
    {
     	  sbi(PORTD,PD5);
	  owrtm1=0;
    }
  else
    {
      cbi(PORTD,PD5);
      owrtm1=1;
    }
	  sbi(ADCSRA,6);
      	  sbi(ADCSRA,6);

	  adcval=(dato<<8)+dato2;



	  
	  

	  // while(!(UCSRA & (1 << UDRE)));
	  //UDR = (unsigned char)0x20;
	  
	    
	  while(!(UCSRA & (1 << UDRE)));
	  UDR = (unsigned char)0x30; 
	    while(!(UCSRA & (1 << UDRE)));
	  UDR = (unsigned char)0x78; 

          
          while(!(UCSRA & (1 << UDRE)));
	  UDR = (unsigned char)(dato&0xf)+48; 
	  if((dato2>>4)<=0x9){
	    while(!(UCSRA & (1 << UDRE)));
	    UDR = (unsigned char)((dato2>>4)+0x30); 
	  }
	  else{

	    while(!(UCSRA & (1 << UDRE)));
	    UDR = (unsigned char)((dato2>>4)+0x57); 
	  }
	  if((dato2&0xf)<=0x9)
	    {
	      while(!(UCSRA & (1 << UDRE)));
	      UDR = (unsigned char)((dato2&0xf)+0x30); 
	    }
	  else{
	    while(!(UCSRA & (1 << UDRE)));
	    UDR = (unsigned char)((dato2&0xf)+0x57); 
	  }

	  while(!(UCSRA & (1 << UDRE)));
	  UDR = (unsigned char)0x20;
 	  
	 	  while(!(UCSRA & (1 << UDRE)));
	    UDR = (unsigned char)0xd;
	  
	  	  while(!(UCSRA & (1 << UDRE)));
	   UDR = (unsigned char)0xa;

	  
	 


	
	  timer1st=0;  

	  TCNT1=0xfe00;
	  // TCNT1=0xfdff;
	  
	  
   


  


}

//Timer1 capture interrupt service subroutine


ISR(ADC_vect)
{
  //dato=vin*1024/2.56
  //vin=dato*2.56/1042
  //vrms=vin*2^.5/2
  dato=ADCH; 
  dato2=ADCL;
    //sbi(ADCSRA,6);
  if(timer1st==1)
    {
      sbi(PORTD,PD4);
      timer1st=0;
    }
  else
    {
      cbi(PORTD,PD4);
      timer1st=1;
    }
  cbi(ADCSRA,6);

  // cbi(PORTD,PD5);
}

ISR(TIMER1_CAPT_vect){
  //This subroutine checks was it start of pulse (rising edge)
  //or was it end (fallingedge)and performs required operations


 }



void usart_putc(unsigned char sdata) {
   // wait until UDR ready
	while(!(UCSRA & (1 << UDRE)))
	  {
	    UCSRB &= ~(1<<TXB8);
	    if ( sdata & 0x0100 )
	      UCSRB |= (1<<TXB8);


	UDR =sdata;    // send character
	  }
}

void initserial(void) {
	// set baud rate
  
  //  UBRRH = (unsigned char)(ubrr>>8);
  // UBRRL = (unsigned char)ubrr;


  	UBRRH = (uint8_t)(UART_BAUD_CALC(UART_BAUD_RATE,F_OSC)>>8);
  	UBRRL = (uint8_t)UART_BAUD_CALC(UART_BAUD_RATE,F_OSC);

	// Enable receiver and transmitter; enable RX interrupt
	//UCSRB = (1 << RXEN) | (1 << TXEN) | (1 << RXCIE);
        UCSRB = (1 << TXEN) ;
	//asynchronous 8N1
       	UCSRC = (1<<URSEL)|(3<<UCSZ0);

}




int main(void) {


int aux=0;
//ADMUX=0xd7;       
//ADCSRA=0xc7;    
//SFIOR=0xc0;

ADMUX=0xc7;       
ADCSRA=0xb8; 

//enable overflow and input capture interrupts
TIMSK=0x44;
//TIFR=0x24
TIFR=0x4;
//Noise canceller, without prescaler, rising edge
TCCR1A=0x0;

TCCR1B=0x1;
//TCCR0=0x1;
TCCR2=0x1;
//TCNT1=0xfdff;
TCNT1=0xfe00;

sbi(DDRD,PD5);
sbi(DDRD,PD4);

sleep_enable();
initserial();
sei();
dato=0x35;
timer1st=0;	
    for (;;) {
 
	
      //       usart_putc("C");
     
      sleep_cpu();
      sleep_disable();
    


    }


}



