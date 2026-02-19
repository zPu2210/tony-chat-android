package com.tonychat.ai.security

import okhttp3.CertificatePinner

/**
 * Factory for creating OkHttp CertificatePinner with pins for all AI provider APIs
 * Pins include both leaf certificates and intermediate CAs for redundancy
 */
object CertificatePinnerFactory {
    /**
     * Create certificate pinner with pins for all AI and backend services
     *
     * Pin hashes obtained via:
     * echo | openssl s_client -connect <host>:443 | openssl x509 -pubkey -noout |
     * openssl pkey -pubin -outform der | openssl dgst -sha256 -binary | base64
     */
    fun create(): CertificatePinner = CertificatePinner.Builder()
        // OpenAI API
        .add("api.openai.com",
            "sha256/y5npFVdBuoqCSOdQa42qiUSPqwMpoei7NK0rQWGUaSU=",  // Leaf cert
            "sha256/kIdp6NNEd8wsugYyyIYFsi1ylMCED3hZbSR8ZFsa/A4="   // Intermediate CA
        )
        // Anthropic API
        .add("api.anthropic.com",
            "sha256/60QDDZy98CjK1XTBTlPbInyzJzi+817KvW+usCk6r+o="   // Leaf cert
        )
        // Remove.bg API
        .add("api.remove.bg",
            "sha256/RKb5xuSTeAiCfMfROqzOcWc/ZE+a8Aby46NK47oEem8="   // Leaf cert
        )
        // Google Generative Language API (Gemini)
        .add("generativelanguage.googleapis.com",
            "sha256/z2h6BLb/h9w+UIAiVtsDKImvDwkUdf6hikiDhPc8vb0="   // Leaf cert
        )
        // Supabase (wildcard for all subdomains)
        .add("*.supabase.co",
            "sha256/p/0tEtQuS5nc34qL4c1JS+XqKizBjtowkWbF6Mw0t9A="   // Leaf cert
        )
        .build()
}
