public class FFT {

    // 주어진 복소수 배열에 대한 FFT를 계산하는 함수
    public static Complex[] fft(Complex[] x) {
        int N = x.length;

        // 입력 신호의 길이가 1인 경우, 재귀 호출을 멈추고 해당 신호를 반환
        if (N == 1) {
            return new Complex[] { x[0] };
        }

        // 입력 신호를 짝수 인덱스와 홀수 인덱스를 가진 두 개의 부분 신호로 분할
        Complex[] even = new Complex[N / 2];
        Complex[] odd = new Complex[N / 2];
        for (int k = 0; k < N / 2; k++) {
            even[k] = x[2 * k];
            odd[k] = x[2 * k + 1];
        }

        // 분할된 부분 신호에 대해 재귀적으로 FFT를 계산
        Complex[] evenFFT = fft(even);
        Complex[] oddFFT = fft(odd);

        // 부분 신호들의 FFT 결과를 결합하여 전체 FFT 결과를 얻음
        Complex[] result = new Complex[N];
        for (int k = 0; k < N / 2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex w = new Complex(Math.cos(kth), Math.sin(kth));
            Complex temp = w.multiply(oddFFT[k]);
            result[k] = evenFFT[k].add(temp);
            result[k + N / 2] = evenFFT[k].subtract(temp);
        }
        return result;
    }

    // 복소수 클래스
    public static class Complex {
        private final double real; // 실수 부분
        private final double imag; // 허수 부분

        public Complex(double real, double imag) {
            this.real = real;
            this.imag = imag;
        }

        public Complex add(Complex other) {
            double newReal = this.real + other.real;
            double newImag = this.imag + other.imag;
            return new Complex(newReal, newImag);
        }

        public Complex subtract(Complex other) {
            double newReal = this.real - other.real;
            double newImag = this.imag - other.imag;
            return new Complex(newReal, newImag);
        }

        public Complex multiply(Complex other) {
            double newReal = this.real * other.real - this.imag * other.imag;
            double newImag = this.real * other.imag + this.imag * other.real;
            return new Complex(newReal, newImag);
        }

        public double abs() {
            return Math.sqrt(real * real + imag * imag);
        }
    }

    public static void main(String[] args) {
        double[] musicSignal = {0.2, 0.5, 0.8, 0.6, 0.3, 0.1, -0.2, -0.4};
        int N = musicSignal.length;
        Complex[] fftData = new Complex[N];
        // 입력 신호로 복소수 배열 생성
        for (int i = 0; i < N; i++) {
            fftData[i] = new Complex(musicSignal[i], 0);
        }

        // FFT 계산
        Complex[] result = fft(fftData);

        // FFT 결과 출력
        for (int i = 0; i < N; i++) {
            double magnitude = result[i].abs();
            double frequency = i / (double) N; // 주파수 값 계산 (0 ~ 1)
            System.out.println("Frequency: " + frequency + ", Magnitude: " + magnitude);
        }
    }
}
