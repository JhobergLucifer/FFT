# by Engineer Jhoberg Quevedo Ruiz cc 82393998 #
Algorit Fast Fouriear Trasform
MATLAB OCTAVE LINUX CAD ALGORITM FAST FOURIEAR TRASFORM 
LANGEiGNE C ALGORITM 
MICROCONTROLLLER 8 BIT ALGORTiM ATMEL CIRCUIT IN CHIP 

```c
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <termios.h>
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <math
#define BAUDRATE B38400
/* change this definition for the correct port */
#define MODEMDEVICE "/dev/ttyS0"
#define _POSIX_SOURCE 1 /* POSIX compliant source */

#define FALSE 0
#define TRUE 1

#define BUFDATOS 60*8*8
#define MUESTRAS 512
#define FSAMPLE 512
#define PI 3.14159265359
#define ADCREF 1.7
 
int fft(int i,int j,float real[],float img[]);
int dato=0;
int fftnormalizada(int datostimeadc[MUESTRAS]);
float datosrealfft[MUESTRAS];
float datosimgfft[MUESTRAS];
float datosabsfft[MUESTRAS];
int converhex(char axcharhex[2]);
//#define BUFDATOS 3360
//#define MUESTRAS 480


volatile int STOP=FALSE;
//end def interface


void * interface (void * sigdato);

//volatile sig_atomic_t okdatos=0;

typedef struct
{
  int okdatos;
}SignalInter;


pthread_t idthreadinter;
pthread_mutex_t mutexdatos;
 SignalInter signalinter;

int main(int argc, char **argv)
 {
   string sline;
   suseconds_t ms;
   double utime;
   char datosbuf[BUFDATOS];
   char *apdatosbuf=datosbuf;
   int sigdato=0;
   int pid;
   // struct sigaction act;
   //memset(&act,'/0',sizeof(act));
   //act.sa_handler=&interface;
   //signal(SIGINT,(void *)interface );

   //interface();
   //system("stty -F /dev/ttyS0 38400 -parenb -parodd cs8 hupcl -cstopb cread clocal -crtscts ignbrk -brkint ignpar -parmrk -inpck -istrip -inlcr -igncr -icrnl -ixon -ixoff -iuclc -ixany -imaxbel -opost -olcuc -ocrnl onlcr -onocr -onlret -ofill -ofdel nl0 cr0 tab0 bs0 vt0 ff0 -isig -icanon -iexten -echo echoe echok -echonl -noflsh -xcase -tostop -echoprt echoctl echoke start 0x79 stop 0xa ");
   int j=0;
   
   /*pid=fork();
   if(pid<0)
     {
       fprintf(stderr,"Fork monitorV error\n");
     }
   else
     {
       if(pid==1)
	 interface(&sigdato);	 
     }
   */
  
   signalinter.okdatos=0;

   pthread_mutex_init(&mutexdatos,NULL);
   int error;
   error=pthread_create(&idthreadinter,NULL,interface,NULL);
   if(error!=0)
     {
       perror("error arrancando thread");
       exit(-1); 
     }

   // interface(&sigdato);   
   while(1)     {
     try {
       //system("cat /dev/ttyS0 | head -n 4098 > /home/micro/vrmstrue/datos.txt");
       //interface(&sigdato);   
       while(signalinter.okdatos==0)
	 {}
       
       pthread_mutex_lock (&mutexdatos);
       signalinter.okdatos=0;
       pthread_mutex_unlock (&mutexdatos);
        

        system("perl /home/micro/vrmstrue/hexa.per");

       /*
       apdatosbuf=stpcpy(apdatosbuf,"a");
       if(j==10)
	 {
	   puts(datosbuf);
	   printf("Datos: %s",datosbuf);
	   free(apdatosbuf);
	   apdatosbuf=datosbuf;
	 }
       j++;
       */
    

       FILE *fplot;
       fplot= popen(GNUPLOT_PATH,"w");
       fputs("set term png \n",fplot);
       fputs("set output \"vrms.png\" \n",fplot);
       fputs( "set autoscale \n",fplot);
       //fprintf(gp, "plot abs(x*sin(sqrt(abs(x))))+400");
       fputs("set xlabel \"tiempo 1 [seg], muestra 1.953125E-3 [seg] \" \n",fplot);
       // fputs("set format x \"%03.0f \" \n",fplot);
       fputs("set xrange [1:512] \n",fplot);
       fputs("set ylabel \"Voltaje [R.M.S]\" \n",fplot);
       //fputs("set format y \"%03.1f \" \n",fplot);
       
       fputs("plot \"datosp.txt\" with lines title 'Monitoreo de Voltaje' ",fplot);
       pclose(fplot);

       system("cp vrms.png  valores.txt /var/www/");
       puts("nueva grafica");

     }
     catch(std::bad_alloc&)
       {
	 cout << "error I/O Memoria"<< endl;
	 pthread_exit(NULL);
       }
   }
   return 0;
 }


int converthex(char axcharhex[255])
{
  int dato1=0,dato2=0,dato3=0,datohex=0;
  char char1=axcharhex[0];
    char char2=axcharhex[1];  
    char char3=axcharhex[2];  
    //  char1='a';
    //char2='b';
    //char3='f';

switch(char1)
   {
  
   case 'a':{ dato1=10;
              break;}
   case 'b': {dato1=11;
	       break;} 
   case 'c': {dato1=12;
       break;}
   case 'd': {dato1=13;break;}
   case 'e': {dato1=14;break;}
   case 'f': {dato1=15;break;}
   case '0': {dato1=0;break;}
   case '1': {dato1=1;break;}
   case '2': {dato1=2;break;}
   case '3': {dato1=3;break;}
   case '4': {dato1=4;break;}
   case '5': {dato1=5;break;}
   case '6': {dato1=6;break;}
   case '7': {dato1=7;break;}
   case '8': {dato1=8;break;}
   case '9': {dato1=9;break;}
   default :{dato1=0;break;}
  }

   switch(char2)
   {
   case 'a': {dato2=10;break;}
   case 'b': {dato2=11;break;} 
   case 'c': {dato2=12;break;}
   case 'd': {dato2=13;break;}
   case 'e': {dato2=14;break;}
   case 'f': {dato2=15;break;}
   case '0': {dato2=0;break;}
   case '1': {dato2=1;break;}
   case '2': {dato2=2;break;}
   case '3': {dato2=3;break;}
   case '4': {dato2=4;break;}
   case '5': {dato2=5;break;}
   case '6': {dato2=6;break;}
   case '7': {dato2=7;break;}
   case '8': {dato2=8;break;}
   case '9': {dato2=9;break;}
   default : {dato2=0;break;}
  }  
  
  switch(char3)
   {
   case 'a': {dato3=10;break;}
   case 'b': {dato3=11;break;} 
   case 'c': {dato3=12;break;}
   case 'd': {dato3=13;break;}
   case 'e': {dato3=14;break;}
   case 'f': {dato3=15;break;}
   case '0': {dato3=0;break;}
   case '1': {dato3=1;break;}
   case '2': {dato3=2;break;}
   case '3': {dato3=3;break;}
   case '4': {dato3=4;break;}
   case '5': {dato3=5;break;}
   case '6': {dato3=6;break;}
   case '7': {dato3=7;break;}
   case '8': {dato3=8;break;}
   case '9': {dato3=9;break;}
   default : {dato3=0;break;}
  }  
  
  datohex=(dato1<<8)+(dato2<<4)+(dato3<<0);
  //datohex=(dato2<<4);
  //printf("%s %c %c %c %d %d %d %d\n",axcharhex,char1,char2,char3,dato1,dato2,dato3,datohex);
//dato1=5;
    // printf("dato1 %c %d ",char1,dato1);     
  return datohex;
}

void * interface(void * sigdato)
{
  int fd,c, res;
  struct termios oldtio,newtio;
  FILE *fdatosinter;
  FILE *fdatosfft;
  size_t len=0;
  char datosbuf[BUFDATOS];
  char *apdatosbuf=datosbuf;
  char *apax=datosbuf;
  char buf[255];
  int datosadc[MUESTRAS];
  char datosbuffft[BUFDATOS];

  //apdatosbuf=(char *)malloc(sizeof(char)*100);
  //apdatosbuf=&datosbuf;
  /*
 Open modem device for reading and writing and not as controlling tty
     because we don't want to get killed if linenoise sends CTRL-C.
  */
  fd = open(MODEMDEVICE, O_RDWR | O_NOCTTY );
  if (fd <0) {perror(MODEMDEVICE); exit(-1); }

  tcgetattr(fd,&oldtio); /* save current serial port settings */
  bzero(&newtio, sizeof(newtio)); /* clear struct for new port settings */

  /*
     BAUDRATE: Set bps rate. You could also use cfsetispeed and cfsetospeed.
     CRTSCTS : output hardware flow control (only used if the cable has
     all necessary lines. See sect. 7 of Serial-HOWTO)
     CS8     : 8n1 (8bit,no parity,1 stopbit)
     CLOCAL  : local connection, no modem contol
     CREAD   : enable receiving characters
  */
  //  newtio.c_cflag = BAUDRATE | CRTSCTS | CS8 | CLOCAL | CREAD | ICRNL |PARODD;
  //newtio.c_cflag = BAUDRATE | CS8 | CLOCAL | CREAD ;
  newtio.c_cflag = BAUDRATE | CS8 | CREAD|CLOCAL| IGNPAR;
  //newtio.c_cflag = BAUDRATE | CS8 | CREAD | CLOCAL  
  //  IGNPAR  : ignore bytes with parity errors
  //   ICRNL   : map CR to NL (otherwise a CR input on the other computer
  //  will not terminate input)
  //  otherwise make device raw (no other input processing)
 
  //newtio.c_iflag = IGNPAR | ICRNL;

  //newtio.c_iflag = PARODD ;
  newtio.c_iflag = PARODD |IGNPAR;
  /*
    Raw output.
  */
  newtio.c_oflag = 0;
 newtio.c_iflag = 0;
  //newtio.c_iflag = ~OPOST;
  /*
    ICANON  : enable canonical input
    disable all echo functionality, and don't send signals to calling program
  */
   newtio.c_lflag = ICANON;
  // newtio.c_lflag = 0;
  /*
    initialize all control characters
     default values can be found in /usr/include/termios.h, and are given
     in the comments, but we don't need them here
  */
  newtio.c_cc[VINTR]    = 0;     /* Ctrl-c */
  newtio.c_cc[VQUIT]    = 0;     /* Ctrl-\ */
  newtio.c_cc[VERASE]   = 0;     /* del */
  newtio.c_cc[VKILL]    = 0;     /* @ */
  newtio.c_cc[VEOF]     = 4;     /* Ctrl-d */
  newtio.c_cc[VTIME]    = 0;     /* inter-character timer unused */
  newtio.c_cc[VMIN]     = 1;     /* blocking read until 1 character arrives */
  newtio.c_cc[VSWTC]    = 0;     /* '\0' */
  newtio.c_cc[VSTART]   = 0;     /* Ctrl-q */
  newtio.c_cc[VSTOP]    = 0;     /* Ctrl-s */
  newtio.c_cc[VSUSP]    = 0;     /* Ctrl-z */
  newtio.c_cc[VEOL]     = 0;     /* '\0' */
  newtio.c_cc[VREPRINT] = 0;     /* Ctrl-r */
  newtio.c_cc[VDISCARD] = 0;     /* Ctrl-u */
  newtio.c_cc[VWERASE]  = 0;     /* Ctrl-w */
  newtio.c_cc[VLNEXT]   = 0;     /* Ctrl-v */
  newtio.c_cc[VEOL2]    = 0;     /* '\0' */

  /*
    now clean the modem line and activate the settings for the port
  */
  tcflush(fd, TCIFLUSH);
  tcsetattr(fd,TCSANOW,&newtio);

  /*
    terminal settings done, now handle input
    In this example, inputting a 'z' at the beginning of a line will
    exit the program.
  */
  int j=0;
  int i=0;
  int muestra=0;
  int dirapdatos = *apdatosbuf;
  char axchar[255];
  int datorx=0;
  while (STOP==FALSE)
    {
      datorx=0;
     /* loop until we have a terminating condition */
    /* read blocks program execution until a line terminating character is
       input, even if more than 255 chars are input. If the number
       of characters read is smaller than the number of chars available,
       subsequent reads will return the remaining chars. res will be set
       to the actual number of characters actually read */
      res = read(fd,buf,255);
      buf[res]=0;

      strcpy(axchar,buf);
      //axchar[0]=(char)buf[0];
      //axchar[1]=(char)buf[1];
      //axchar[2]=(char)buf[2];

       datorx=converthex(axchar);
       //puts(buf);
      datosadc[j]=datorx;
      // printf("%s  %d \n",buf,datorx);
      // apdatosbuf=stpcpy(apdatosbuf,buf);
      strcat(datosbuf,buf);

   //puts(datosbuf);
    //memcpy(datosbuf,buf,strlen(buf));
    //buf[res]=0;             /* set end of string, so we can printf */
    //printf(":%s,%d\n", buf, res);
    //printf("transimosion :%d %s \n", j,buf);
    j++;
    if(j==MUESTRAS)
      {
        j=0;	


        remove ("datos.txt");
        fdatosinter=fopen("datos.txt","w+");
        if(fdatosinter==NULL)
          {
	    fclose(fdatosinter);
	    puts("error leyecdo archivo datos");
          }
        else
          {



	    //puts(datosbuf);
                len=strlen(datosbuf);
                fwrite(datosbuf,len,1,fdatosinter);
                //memset(datosbuf,0,BUFDATOS);
                //fflush(*apdatosbuf);
                //apdatosbuf=memset(apdatosbuf,0,BUFDATOS);
                //apdatosbuf=strcpy(apdatosbuf,"");


              

                //apdatosbuf=NULL;
                 //*apdatosbuf=NULL;
                //apdatosbuf=&datosbuf;
                //apdatosbuf=datosbuf;
                //apdatosbuf=strcpy(apdatosbuf,"");
                //puts(datosbuf);
                // apdatosbuf=datosbuf;
	


          }
        fclose(fdatosinter);
	strcpy(datosbuf,"");
	//*apdatosbuf=NULL;
	//free(apdatosbuf);
	//apdatosbuf=(char *)malloc(sizeof(char)*BUFDATOS);

        pthread_mutex_lock (&mutexdatos);
        signalinter.okdatos=1;
	pthread_mutex_unlock (&mutexdatos);
  

	fftnormalizada(datosadc);

	for(i=0;i<MUESTRAS;i++)
	  {
	    len=sprintf(axchar,"%f\n",datosabsfft[i]);
	    strcat(datosbuffft,axchar);
	  }
	//puts(datosbuffft);


        
	remove ("datosfft.txt");
	fdatosfft=fopen("datosfft.txt","w+");
	if(fdatosfft==NULL)
          {
	    fclose(fdatosfft);
	    puts("error leyecdo archivo datos");
          }
        else
          {
	    
	     len=strlen(datosbuffft);
	     fwrite(datosbuffft,len,1,fdatosfft);

          }
        fclose(fdatosfft);
	strcpy(datosbuffft,"");


	//        pthread_mutex_lock (&mutexdatos);
        //signalinter.okdatos=1;
	//pthread_mutex_unlock (&mutexdatos);



      }
    if (buf[0]=='z')
      STOP=TRUE;
  }
  /* restore the old port settings */
  tcsetattr(fd,TCSANOW,&oldtio);
  //exit(sigdato);
}


//** function fft
// i : 1 transformada inversa, :-1 trasnformada
// j : NMUESTRAS
// real : argumento real
// img: argumento imaginario
int fft(int i, int j, float real[], float img[])
{

float f = sqrt(1.0F / (float)j);
int i1;
int k;
for( k = i1 = 0; k < j; k++)

{

if(i1 >= k)
{
float f1 = (real[i1]) * f;
float f2 = (img[i1]) * f;
real[i1] = (real[k]) * f;
img[i1] = (img[k]) * f;
real[k] = f1;
img[k] = f2;
}

int k1;
for(k1 = j / 2; k1 >= 1 && i1 >= k1; k1 /= 2)
i1 -= k1;


i1 += k1;
}


int l1 = 1;
int i2;

for( i2 = 2 * l1; l1 < j; i2 = 2 * l1)
{

float f3 = ((float)i * 3.141593F) / (float)l1;
int j2;
for( j2 = 0; j2 < l1; j2++)
{
float f4 = (float)j2 * f3;
float f5 = (float)cos(f4);
float f6 = (float)sin(f4);
int l;
for( l = j2; l < j; l += i2)
{
int j1 = l + l1;
float f7 = f5 * (real[j1]) - f6 * (img[j1]);
float f8 = f5 * (img[j1]) + f6 * (real[j1]);
real[j1] = real[l] - f7;
img[j1] = img[l] - f8;
real[l] += f7;
img[l] += f8;
}

}

l1 = i2;
l1 = i2;


}

return 1;
}
//**********end FFT **************


int fftnormalizada(int datostimeadc[MUESTRAS])
{
int var,len,k;
char outstring;
char buferr[10];
float promedio,M,prompos;
float w[MUESTRAS];
float f[MUESTRAS]; 

 for(k=0;k<MUESTRAS;k++)
      datostimeadc[k]=1*sin((2*3.1415927*k*60/512));


 for(k = 0; k < MUESTRAS; k++)
   f[k] = k*FSAMPLE/(MUESTRAS);
 for(k = 0 ;k< MUESTRAS; k++)
   w[k] = 2*PI*f[k];
 
 
     
     
     k=0;
     promedio=0;
     while( k<MUESTRAS)
       {
	 // al=(2*(al-am)/(aM-am))-1;
	 //al=al*100;
	 datosrealfft[k] =datostimeadc[k], promedio += datostimeadc[k] ;
	 k++;
       }
     M=k;
     
     promedio /= M;
     for(k = 0; k<M; k++){
       datosrealfft[k] -= promedio;
      
     }

     //FFT 
//     fft(-1,MUESTRAS/2,datosrealfft,datosimgfft);
 fft(-1,MUESTRAS,datosrealfft,datosimgfft);
 for(k=0;k<MUESTRAS;k++)
   {
    
     //datosabsfft[k]=sqrt (datosrealfft[k]*datosrealfft[k]/MUESTRAS+datosimgfft[k]*datosimgfft[k]/MUESTRAS);
    
     datosabsfft[k]= 2*sqrt(datosrealfft[k]*datosrealfft[k]/(MUESTRAS*MUESTRAS)+datosimgfft[k]*datosimgfft[k]/(MUESTRAS*MUESTRAS));
   }

        
 return 1;
}

```
