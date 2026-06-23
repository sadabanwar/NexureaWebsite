import api from './api';
import { PaymentOrder, PaymentVerification } from '../types';

declare global {
  interface Window {
    Razorpay: any;
  }
}

export const paymentService = {
  createOrder: async (packageId: number, referralCode?: string): Promise<PaymentOrder> => {
    const response = await api.post<PaymentOrder>('/payments/create-order', {
      packageId,
      referralCode,
    });
    return response.data;
  },

  verifyPayment: async (verification: PaymentVerification): Promise<void> => {
    await api.post('/payments/verify', verification);
  },

  handlePayment: async (
    packageId: number,
    referralCode?: string,
    onSuccess?: () => void,
    onError?: (error: string) => void
  ): Promise<void> => {
    try {
      const order = await paymentService.createOrder(packageId, referralCode);

      const options = {
        key: process.env.REACT_APP_RAZORPAY_KEY,
        amount: order.amount,
        currency: order.currency,
        order_id: order.orderId,
        name: 'Nexurea University',
        description: 'Course Package Purchase',
        handler: async (response: any) => {
          try {
            await paymentService.verifyPayment({
              razorpayOrderId: response.razorpay_order_id,
              razorpayPaymentId: response.razorpay_payment_id,
              razorpaySignature: response.razorpay_signature,
            });
            if (onSuccess) {
              onSuccess();
            }
          } catch (error: any) {
            if (onError) {
              onError(error.response?.data?.message || 'Payment verification failed');
            }
          }
        },
        prefill: {
          name: '',
          email: '',
          contact: '',
        },
        theme: {
          color: '#1a237e',
        },
      };

      const rzp = new window.Razorpay(options);

      rzp.on('payment.failed', (response: any) => {
        if (onError) {
          onError(response.error.description || 'Payment failed');
        }
      });

      rzp.open();
    } catch (error: any) {
      if (onError) {
        onError(error.response?.data?.message || 'Failed to create order');
      }
    }
  },
};
