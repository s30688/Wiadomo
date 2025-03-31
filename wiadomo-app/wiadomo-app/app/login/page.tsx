'use client'

import { useState } from 'react'
import { createBrowserClient } from '@supabase/ssr'

const supabase = createBrowserClient(
    process.env.NEXT_PUBLIC_SUPABASE_URL!,
    process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY!
)

export default function LoginPage() {
    const [email, setEmail] = useState('')
    const [loading, setLoading] = useState(false)

    const handleLogin = async () => {
        setLoading(true)
        const { error } = await supabase.auth.signInWithOtp({ email })

        if (error) alert('❌ Błąd: ' + error.message)
        else alert('📩 Sprawdź e-mail i kliknij magic link')

        setLoading(false)
    }

    return (
        <div className="max-w-md mx-auto mt-20 p-4 border rounded shadow">
            <h1 className="text-2xl font-bold mb-4">Logowanie</h1>
            <input
                type="email"
                className="w-full p-2 border mb-4"
                placeholder="Twój e-mail"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <button
                onClick={handleLogin}
                disabled={loading}
                className="bg-blue-600 text-white w-full py-2 rounded"
            >
                {loading ? 'Wysyłanie...' : 'Zaloguj się'}
            </button>
        </div>
    )
}
