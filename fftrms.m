%calculo potencia mediante series de Fourier
Vmax=1.70;
Imax=1.4142;
f=60;
%frecuencia de muestreo
Fs = 1024;
%periodo de muestreo
T = 1/Fs;                     % Sample time
%numero de datos
N = 1024;
%vector de tiempo
t = (0:N-1)*T;
% Sum of a 60 Hz sinusoid and a 120 Hz sinusoid
v = Vmax*cos(2*pi*f*t) ;

i = Imax*cos(2*pi*f*t);     % Sinusoids plus noise
hold on
paintv=plot(t(1:60),v(1:60));
set(paintv,'Color','blue');
title('Ondas de voltaje y corriente');
%ylabel('Voltaje');
xlabel('timpo (milisegundos)');
painti=plot(t(1:60),i(1:60));
set(painti,'Color','green');
hold off

%calculo potencia instanea

%calculo FFT de corriente
NFFT = 2^nextpow2(N);
ffti = fft(i,NFFT)/N;
fifft = Fs/2*linspace(0,1,NFFT/2+1);
plot(fifft,2*abs(ffti(1:NFFT/2+1)));

title('Amplitud del espectro de i(t)')
xlabel('Frecuencia (Hz)')
ylabel('|I(f)|')

%calculo FFT de voltaje
NFFT = 2^nextpow2(N);
fftv = fft(v,NFFT)/N;
fvfft = Fs/2*linspace(0,1,NFFT/2+1);
plot(fvfft,2*abs(fftv(1:NFFT/2+1)));

title('Amplitud del espectro de v(t)')
xlabel('Frecuencia (Hz)')
ylabel('|V(f)|')
%calculo potencia
pactivatotal=0
for k=1:N/2
        complejov=fftv(k);
        angulov=atand(imag(complejov)/real(complejov));
        magnitudv=2*abs(complejov);

        complejoi=ffti(k);
        anguloi=atand(imag(complejov)/real(complejov));
        magnitudi=2*abs(complejoi);

        pactiva(k)=1/2*magnitudv*magnitudi*cos(angulov-anguloi);
        pactivatotal=pactivatotal+pactiva(k);

        preactiva(k)=1/2*magnitudv*magnitudi*sin(angulov-anguloi);
        paparente(k)=complex(pactiva(k),preactiva(k));

end

pactivatotal
