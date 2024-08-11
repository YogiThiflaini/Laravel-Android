<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Casts\Attribute;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Mahasiswa extends Model
{   
    use HasFactory;
    protected $fillable = [
        'namamahasiswa',
        'nim',
        'alamat',
        'gender',
        'agama',
        'usia',
        'image',
    ];

    protected function image(): Attribute{
        return Attribute::make(
            get: fn ($image) => asset('/storage/mahasiswa/' . $image)
        );
    }
}
